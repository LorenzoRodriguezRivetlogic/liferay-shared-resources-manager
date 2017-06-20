/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.rivetlogic.assetmanagement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.rivetlogic.assetmanagement.keys.AssetNotificationsKeys;
import com.rivetlogic.assetmanagement.model.Asset;
import com.rivetlogic.assetmanagement.model.AssetMessage;
import com.rivetlogic.assetmanagement.service.AssetLocalServiceUtil;
import com.rivetlogic.assetmanagement.service.base.AssetMessageLocalServiceBaseImpl;

import aQute.bnd.annotation.ProviderType;

/**
 * The implementation of the asset message local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.rivetlogic.assetmanagement.service.AssetMessageLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetMessageLocalServiceBaseImpl
 * @see com.rivetlogic.assetmanagement.service.AssetMessageLocalServiceUtil
 */
@ProviderType
public class AssetMessageLocalServiceImpl
extends AssetMessageLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.rivetlogic.assetmanagement.service.AssetMessageLocalServiceUtil} to access the asset message local service.
	 */

	public AssetMessage addAssetMessage(ThemeDisplay themeDisplay, ServiceContext serviceContext, AssetMessage assetMessage, String notificationType) throws SystemException,
	PortalException {

		long assetMessageId = counterLocalService.increment(AssetMessage.class.getName());
		Date now = new Date();

		AssetMessage message = assetMessagePersistence.create(assetMessageId);

		message.setCompanyId(themeDisplay.getCompanyId());
		message.setGroupId(themeDisplay.getScopeGroupId());

		message.setFromUserId(themeDisplay.getUserId());

		message.setMessage(assetMessage.getMessage());

		message.setCreateDate(now);

		message.setAssetId(assetMessage.getAssetId());
		message.setToUserId(assetMessage.getToUserId());
		message.setMessage(assetMessage.getMessage());

		sendNotificiationMessage(serviceContext, message, notificationType);

		return assetMessageLocalService.addAssetMessage(message);
	}

	@SuppressWarnings("unchecked")
	public List<AssetMessage> getAssetMessages(long assetId, long userId, int start, int end) {

		List<AssetMessage> myList = new ArrayList<AssetMessage>();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetMessage.class);

		dynamicQuery.add(PropertyFactoryUtil.forName("assetId").eq(assetId));

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		disjunction.add(PropertyFactoryUtil.forName("fromUserId").eq(userId));
		disjunction.add(PropertyFactoryUtil.forName("toUserId").eq(userId));

		dynamicQuery.add(disjunction);

		dynamicQuery.setLimit(start, end);

		dynamicQuery.addOrder(OrderFactoryUtil.desc("createDate"));

		try {
			myList = assetMessagePersistence.findWithDynamicQuery(dynamicQuery);
		} catch (SystemException e) {
			_log.error(e.getMessage());
		}
		return myList;
	}

	public int getAssetMessagesCount(long assetId, long userId) {

		int count = 0;

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetMessage.class);

		dynamicQuery.add(PropertyFactoryUtil.forName("assetId").eq(assetId));

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		disjunction.add(PropertyFactoryUtil.forName("fromUserId").eq(userId));
		disjunction.add(PropertyFactoryUtil.forName("toUserId").eq(userId));

		dynamicQuery.add(disjunction);

		try {
			count = (int) assetMessagePersistence.countWithDynamicQuery(dynamicQuery);
		} catch (SystemException e) {
			_log.error(e.getMessage());
		}
		return count;
	}

	private void sendNotificiationMessage(ServiceContext serviceContext, AssetMessage assetMessage,  String notificationType)
			throws PortalException, SystemException {

		JSONObject notificationEventJSONObject = JSONFactoryUtil.createJSONObject();

		Asset asset = AssetLocalServiceUtil.getAsset(assetMessage.getAssetId());

		notificationEventJSONObject.put("assetId", asset.getAssetId());
		notificationEventJSONObject.put("assetName", asset.getName());

		notificationEventJSONObject.put("fromUserId", assetMessage.getFromUserId());

		notificationEventJSONObject.put("assetMessageId", assetMessage.getAssetMessageId());

		notificationEventJSONObject.put("notificationType", notificationType);

		notificationEventJSONObject.put("message", assetMessage.getMessage());

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(assetMessage.getToUserId(), AssetNotificationsKeys.PORTLET_ID,
				new Date().getTime(), assetMessage.getFromUserId(), notificationEventJSONObject.toString(), false, serviceContext);

	}
	private static final Log _log = LogFactoryUtil.getLog(AssetMessageLocalServiceImpl.class);

}
