package com.rivetlogic.assetmanagement.portlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import com.rivetlogic.assetmanagement.model.Asset;
import com.rivetlogic.assetmanagement.model.AssetCategory;
import com.rivetlogic.assetmanagement.model.AssetLocation;
import com.rivetlogic.assetmanagement.service.AssetCategoryLocalServiceUtil;
import com.rivetlogic.assetmanagement.service.AssetLocalServiceUtil;
import com.rivetlogic.assetmanagement.service.AssetLocationLocalServiceUtil;
import com.rivetlogic.assetmanagement.service.AssetRequestLocalServiceUtil;

/**
 * @author emmanuelabarca
 */
@Component(
	immediate = true,
	property = {
			"javax.portlet.name=manage-assets",
			"com.liferay.portlet.icon=/icon.png",
			"com.liferay.portlet.display-category=site_administration.content",
			"com.liferay.portlet.control-panel-entry-weight=1.0",
			"com.liferay.portlet.header-portlet-css=/css/main.css",
			"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
			"com.liferay.portlet.css-class-wrapper=manage-assets-portlet",
			"com.liferay.portlet.instanceable=false",
			"javax.portlet.init-param.template-path=/",
			"javax.portlet.init-param.view-template=/manageassetsportlet/view.jsp",
			"javax.portlet.init-param.help-template=/help.jsp",
			"javax.portlet.portlet-mode=text/html;view,help",
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=administrator,power-user,user,guest",
			"javax.portlet.init-param.copy-request-parameters=false",
	},
	service = Portlet.class
)
public class ManageAssetsPortlet extends MVCPortlet {

	public static final String PORTLET_ID = "manageassets_WAR_sharedresourcesmanagerportlet";

	public void addAsset(ActionRequest request, ActionResponse response) throws IOException, PortalException, SystemException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Asset.class.getName(), request);

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		Asset asset = AssetLocalServiceUtil.createAsset(0);

		asset.setName(ParamUtil.getString(request, "name"));
		asset.setDescription(ParamUtil.getString(request, "description"));
		asset.setLocation(ParamUtil.getLong(request, "location"));
		asset.setActive(ParamUtil.getBoolean(request, "active"));
		asset.setCategory(ParamUtil.getLong(request, "category"));

		List<String> errors = new ArrayList<String>();

		if (NewAssetValidator.validateAsset(asset, errors)) {

			long assetId = AssetLocalServiceUtil.addAsset(themeDisplay, serviceContext, asset);

			SessionMessages.add(request, "asset-success-created");

			response.setRenderParameter("assetId", String.valueOf(assetId));
			response.setRenderParameter("mvcPath", "/html/manageassets/edit_asset.jsp");

		} else {
			for (String error : errors) {
				SessionErrors.add(request, error);
			}

			PortalUtil.copyRequestParameters(request, response);

			response.setRenderParameter("mvcPath", "/html/manageassets/add_asset.jsp");

		}
	}

	public void deleteAsset(ActionRequest request, ActionResponse response) throws PortalException, SystemException, IOException {

		long assetId = ParamUtil.getLong(request, "assetId");

		AssetRequestLocalServiceUtil.deleteAssetRequestByAssetId(assetId);
		AssetLocalServiceUtil.deleteAsset(assetId);

		sendRedirect(request, response);
	}

	public void editAssetPhoto(ActionRequest request, ActionResponse response) throws SystemException, PortalException, IOException, SQLException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Asset.class.getName(), request);

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		UploadRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);

		long assetId = ParamUtil.getLong(uploadRequest, "assetId");

		if (assetId == 0) {
			assetId = ParamUtil.getLong(request, "assetId");
		}

		if (assetId > 0) {

			Asset asset = AssetLocalServiceUtil.getAsset(assetId);

			try {

				asset.setPhoto(null);

				File photoImage = uploadRequest.getFile("photo");

				if (photoImage != null) {

					InputStream fis = new FileInputStream(photoImage);
					OutputBlob photo = new OutputBlob(fis, photoImage.length());

					asset.setPhoto(photo);
					asset.setMimeType(MimeTypesUtil.getContentType(photoImage));
				}
			} catch (FileNotFoundException ex) {
				_log.error("No file uploaded");
			} catch (Exception ex) {
				_log.error(ex);
			}

			if (Validator.isNotNull(asset.getPhoto())) {

				AssetLocalServiceUtil.editAsset(themeDisplay, serviceContext, asset);

				sendRedirect(request, response);

			} else {

				SessionErrors.add(request, "asset-photo-required");

				PortalUtil.copyRequestParameters(request, response);

				response.setRenderParameter("assetId", assetId+"");

				response.setRenderParameter("mvcPath", "/html/manageassets/edit_asset.jsp");
			}

		} else {

			SessionErrors.add(request, "asset-photo-required");

			response.setRenderParameter("mvcPath", "/html/manageassets/view.jsp");

		}
	}

	public void editAsset(ActionRequest request, ActionResponse response) throws SystemException, PortalException, IOException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Asset.class.getName(), request);

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		long assetId = ParamUtil.getLong(request, "assetId");

		Asset asset = AssetLocalServiceUtil.getAsset(assetId);

		asset.setName(ParamUtil.getString(request, "name"));
		asset.setDescription(ParamUtil.getString(request, "description"));
		asset.setLocation(ParamUtil.getLong(request, "location"));
		asset.setActive(ParamUtil.getBoolean(request, "active"));
		asset.setCategory(ParamUtil.getLong(request, "category"));

		List<String> errors = new ArrayList<String>();

		if (NewAssetValidator.validateAsset(asset, errors)) {

			AssetLocalServiceUtil.editAsset(themeDisplay, serviceContext, asset);

			sendRedirect(request, response);

		} else {
			for (String error : errors) {
				SessionErrors.add(request, error);
			}

			PortalUtil.copyRequestParameters(request, response);

			response.setRenderParameter("mvcPath", "/html/manageassets/edit_asset.jsp");
		}

	}

	public void addAssetCategory(ActionRequest request, ActionResponse response) throws SystemException, PortalException, IOException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Asset.class.getName(), request);

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		long categoryId = ParamUtil.getLong(request, "id", -1);
		String categoryName = ParamUtil.getString(request, "name");

		if(categoryId == -1) {
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.createAssetCategory(0);
			assetCategory.setName(categoryName);
			AssetCategoryLocalServiceUtil.addAssetCategory(themeDisplay, serviceContext, assetCategory);
		} else {
			AssetCategory assetCategory = AssetCategoryLocalServiceUtil.fetchAssetCategory(categoryId);
			assetCategory.setName(categoryName);
			AssetCategoryLocalServiceUtil.updateAssetCategory(assetCategory);
		}

	}

	public void deleteAssetCategory(ActionRequest request, ActionResponse response) throws SystemException, PortalException, IOException {

		long assetCategoryId = ParamUtil.getLong(request, "assetCategoryId");

		AssetCategoryLocalServiceUtil.deleteAssetCategory(assetCategoryId);

	}

	public void addAssetLocation(ActionRequest request, ActionResponse response) throws SystemException, PortalException, IOException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Asset.class.getName(), request);

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

		long categoryId = ParamUtil.getLong(request, "id", -1);
		String categoryName = ParamUtil.getString(request, "name");

		if(categoryId == -1) {
			AssetLocation assetLocation = AssetLocationLocalServiceUtil.createAssetLocation(0);
			assetLocation.setName(categoryName);
			AssetLocationLocalServiceUtil.addAssetLocation(themeDisplay, serviceContext, assetLocation);
		} else {
			AssetLocation assetLocation = AssetLocationLocalServiceUtil.fetchAssetLocation(categoryId);
			assetLocation.setName(categoryName);
			AssetLocationLocalServiceUtil.updateAssetLocation(assetLocation);
		}

	}

	public void deleteAssetLocation(ActionRequest request, ActionResponse response) throws SystemException, PortalException, IOException {

		long assetLocationId = ParamUtil.getLong(request, "assetLocationId");

		AssetLocationLocalServiceUtil.deleteAssetLocation(assetLocationId);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException, PortletException {

		long assetId = ParamUtil.getLong(resourceRequest, "assetId");

		if (assetId > 0) {
			try {
				Asset asset = AssetLocalServiceUtil.getAsset(assetId);

				if (asset != null) {
					Blob image = asset.getPhoto();
					if (image != null) {
						byte[] imgData = image.getBytes(1, (int) image.length());
						resourceResponse.setContentType(asset.getMimeType());
						OutputStream o = resourceResponse.getPortletOutputStream();
						o.write(imgData);
						o.flush();
						o.close();
					} else {

						String realPath = getPortletContext().getRealPath("/");

						String url = realPath + "images/no-preview-available.jpg";

						Path path = Paths.get(url);
						byte[] data = Files.readAllBytes(path);

						resourceResponse.setContentType("image/jpeg");

						OutputStream o = resourceResponse.getPortletOutputStream();

						o.write(data);

						o.flush();
						o.close();
					}
				}

			} catch (Exception e) {
				_log.error(e);
			}
		} else {

			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(ManageAssetsPortlet.class);

}
