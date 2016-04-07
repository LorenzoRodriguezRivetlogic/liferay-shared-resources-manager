/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.rivetlogic.assetmanagement.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import com.rivetlogic.assetmanagement.model.AssetRequest;
import com.rivetlogic.assetmanagement.model.AssetRequestModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the AssetRequest service. Represents a row in the &quot;rivetlogic_AssetRequest&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.rivetlogic.assetmanagement.model.AssetRequestModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AssetRequestImpl}.
 * </p>
 *
 * @author Manrique Varela
 * @see AssetRequestImpl
 * @see com.rivetlogic.assetmanagement.model.AssetRequest
 * @see com.rivetlogic.assetmanagement.model.AssetRequestModel
 * @generated
 */
public class AssetRequestModelImpl extends BaseModelImpl<AssetRequest>
	implements AssetRequestModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset request model instance should use the {@link com.rivetlogic.assetmanagement.model.AssetRequest} interface instead.
	 */
	public static final String TABLE_NAME = "rivetlogic_AssetRequest";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", Types.VARCHAR },
			{ "assetRequestId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "assetId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "companyId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "requestedDate", Types.TIMESTAMP },
			{ "assingedDate", Types.TIMESTAMP },
			{ "bookedDate", Types.TIMESTAMP },
			{ "returnedDate", Types.TIMESTAMP },
			{ "status", Types.VARCHAR },
			{ "priority", Types.VARCHAR },
			{ "description", Types.VARCHAR }
		};
	public static final String TABLE_SQL_CREATE = "create table rivetlogic_AssetRequest (uuid_ VARCHAR(75) null,assetRequestId LONG not null primary key,groupId LONG,assetId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,companyId LONG,userName VARCHAR(75) null,requestedDate DATE null,assingedDate DATE null,bookedDate DATE null,returnedDate DATE null,status VARCHAR(75) null,priority VARCHAR(75) null,description VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table rivetlogic_AssetRequest";
	public static final String ORDER_BY_JPQL = " ORDER BY assetRequest.assetRequestId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY rivetlogic_AssetRequest.assetRequestId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.rivetlogic.assetmanagement.model.AssetRequest"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.rivetlogic.assetmanagement.model.AssetRequest"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.rivetlogic.assetmanagement.model.AssetRequest"),
			true);
	public static long COMPANYID_COLUMN_BITMASK = 1L;
	public static long GROUPID_COLUMN_BITMASK = 2L;
	public static long USERID_COLUMN_BITMASK = 4L;
	public static long UUID_COLUMN_BITMASK = 8L;
	public static long ASSETREQUESTID_COLUMN_BITMASK = 16L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.rivetlogic.assetmanagement.model.AssetRequest"));

	public AssetRequestModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _assetRequestId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAssetRequestId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetRequestId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AssetRequest.class;
	}

	@Override
	public String getModelClassName() {
		return AssetRequest.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("assetRequestId", getAssetRequestId());
		attributes.put("groupId", getGroupId());
		attributes.put("assetId", getAssetId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("companyId", getCompanyId());
		attributes.put("userName", getUserName());
		attributes.put("requestedDate", getRequestedDate());
		attributes.put("assingedDate", getAssingedDate());
		attributes.put("bookedDate", getBookedDate());
		attributes.put("returnedDate", getReturnedDate());
		attributes.put("status", getStatus());
		attributes.put("priority", getPriority());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long assetRequestId = (Long)attributes.get("assetRequestId");

		if (assetRequestId != null) {
			setAssetRequestId(assetRequestId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long assetId = (Long)attributes.get("assetId");

		if (assetId != null) {
			setAssetId(assetId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date requestedDate = (Date)attributes.get("requestedDate");

		if (requestedDate != null) {
			setRequestedDate(requestedDate);
		}

		Date assingedDate = (Date)attributes.get("assingedDate");

		if (assingedDate != null) {
			setAssingedDate(assingedDate);
		}

		Date bookedDate = (Date)attributes.get("bookedDate");

		if (bookedDate != null) {
			setBookedDate(bookedDate);
		}

		Date returnedDate = (Date)attributes.get("returnedDate");

		if (returnedDate != null) {
			setReturnedDate(returnedDate);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		String priority = (String)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public String getUuid() {
		if (_uuid == null) {
			return StringPool.BLANK;
		}
		else {
			return _uuid;
		}
	}

	@Override
	public void setUuid(String uuid) {
		if (_originalUuid == null) {
			_originalUuid = _uuid;
		}

		_uuid = uuid;
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	@Override
	public long getAssetRequestId() {
		return _assetRequestId;
	}

	@Override
	public void setAssetRequestId(long assetRequestId) {
		_columnBitmask = -1L;

		_assetRequestId = assetRequestId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_columnBitmask |= GROUPID_COLUMN_BITMASK;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = _groupId;
		}

		_groupId = groupId;
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	@Override
	public long getAssetId() {
		return _assetId;
	}

	@Override
	public void setAssetId(long assetId) {
		_assetId = assetId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_columnBitmask |= USERID_COLUMN_BITMASK;

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = _userId;
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return StringPool.BLANK;
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getRequestedDate() {
		return _requestedDate;
	}

	@Override
	public void setRequestedDate(Date requestedDate) {
		_requestedDate = requestedDate;
	}

	@Override
	public Date getAssingedDate() {
		return _assingedDate;
	}

	@Override
	public void setAssingedDate(Date assingedDate) {
		_assingedDate = assingedDate;
	}

	@Override
	public Date getBookedDate() {
		return _bookedDate;
	}

	@Override
	public void setBookedDate(Date bookedDate) {
		_bookedDate = bookedDate;
	}

	@Override
	public Date getReturnedDate() {
		return _returnedDate;
	}

	@Override
	public void setReturnedDate(Date returnedDate) {
		_returnedDate = returnedDate;
	}

	@Override
	public String getStatus() {
		if (_status == null) {
			return StringPool.BLANK;
		}
		else {
			return _status;
		}
	}

	@Override
	public void setStatus(String status) {
		_status = status;
	}

	@Override
	public String getPriority() {
		if (_priority == null) {
			return StringPool.BLANK;
		}
		else {
			return _priority;
		}
	}

	@Override
	public void setPriority(String priority) {
		_priority = priority;
	}

	@Override
	public String getDescription() {
		if (_description == null) {
			return StringPool.BLANK;
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(PortalUtil.getClassNameId(
				AssetRequest.class.getName()));
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			AssetRequest.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AssetRequest toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (AssetRequest)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		AssetRequestImpl assetRequestImpl = new AssetRequestImpl();

		assetRequestImpl.setUuid(getUuid());
		assetRequestImpl.setAssetRequestId(getAssetRequestId());
		assetRequestImpl.setGroupId(getGroupId());
		assetRequestImpl.setAssetId(getAssetId());
		assetRequestImpl.setUserId(getUserId());
		assetRequestImpl.setCreateDate(getCreateDate());
		assetRequestImpl.setModifiedDate(getModifiedDate());
		assetRequestImpl.setCompanyId(getCompanyId());
		assetRequestImpl.setUserName(getUserName());
		assetRequestImpl.setRequestedDate(getRequestedDate());
		assetRequestImpl.setAssingedDate(getAssingedDate());
		assetRequestImpl.setBookedDate(getBookedDate());
		assetRequestImpl.setReturnedDate(getReturnedDate());
		assetRequestImpl.setStatus(getStatus());
		assetRequestImpl.setPriority(getPriority());
		assetRequestImpl.setDescription(getDescription());

		assetRequestImpl.resetOriginalValues();

		return assetRequestImpl;
	}

	@Override
	public int compareTo(AssetRequest assetRequest) {
		int value = 0;

		if (getAssetRequestId() < assetRequest.getAssetRequestId()) {
			value = -1;
		}
		else if (getAssetRequestId() > assetRequest.getAssetRequestId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetRequest)) {
			return false;
		}

		AssetRequest assetRequest = (AssetRequest)obj;

		long primaryKey = assetRequest.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		AssetRequestModelImpl assetRequestModelImpl = this;

		assetRequestModelImpl._originalUuid = assetRequestModelImpl._uuid;

		assetRequestModelImpl._originalGroupId = assetRequestModelImpl._groupId;

		assetRequestModelImpl._setOriginalGroupId = false;

		assetRequestModelImpl._originalUserId = assetRequestModelImpl._userId;

		assetRequestModelImpl._setOriginalUserId = false;

		assetRequestModelImpl._originalCompanyId = assetRequestModelImpl._companyId;

		assetRequestModelImpl._setOriginalCompanyId = false;

		assetRequestModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AssetRequest> toCacheModel() {
		AssetRequestCacheModel assetRequestCacheModel = new AssetRequestCacheModel();

		assetRequestCacheModel.uuid = getUuid();

		String uuid = assetRequestCacheModel.uuid;

		if ((uuid != null) && (uuid.length() == 0)) {
			assetRequestCacheModel.uuid = null;
		}

		assetRequestCacheModel.assetRequestId = getAssetRequestId();

		assetRequestCacheModel.groupId = getGroupId();

		assetRequestCacheModel.assetId = getAssetId();

		assetRequestCacheModel.userId = getUserId();

		Date createDate = getCreateDate();

		if (createDate != null) {
			assetRequestCacheModel.createDate = createDate.getTime();
		}
		else {
			assetRequestCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			assetRequestCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			assetRequestCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		assetRequestCacheModel.companyId = getCompanyId();

		assetRequestCacheModel.userName = getUserName();

		String userName = assetRequestCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			assetRequestCacheModel.userName = null;
		}

		Date requestedDate = getRequestedDate();

		if (requestedDate != null) {
			assetRequestCacheModel.requestedDate = requestedDate.getTime();
		}
		else {
			assetRequestCacheModel.requestedDate = Long.MIN_VALUE;
		}

		Date assingedDate = getAssingedDate();

		if (assingedDate != null) {
			assetRequestCacheModel.assingedDate = assingedDate.getTime();
		}
		else {
			assetRequestCacheModel.assingedDate = Long.MIN_VALUE;
		}

		Date bookedDate = getBookedDate();

		if (bookedDate != null) {
			assetRequestCacheModel.bookedDate = bookedDate.getTime();
		}
		else {
			assetRequestCacheModel.bookedDate = Long.MIN_VALUE;
		}

		Date returnedDate = getReturnedDate();

		if (returnedDate != null) {
			assetRequestCacheModel.returnedDate = returnedDate.getTime();
		}
		else {
			assetRequestCacheModel.returnedDate = Long.MIN_VALUE;
		}

		assetRequestCacheModel.status = getStatus();

		String status = assetRequestCacheModel.status;

		if ((status != null) && (status.length() == 0)) {
			assetRequestCacheModel.status = null;
		}

		assetRequestCacheModel.priority = getPriority();

		String priority = assetRequestCacheModel.priority;

		if ((priority != null) && (priority.length() == 0)) {
			assetRequestCacheModel.priority = null;
		}

		assetRequestCacheModel.description = getDescription();

		String description = assetRequestCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			assetRequestCacheModel.description = null;
		}

		return assetRequestCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", assetRequestId=");
		sb.append(getAssetRequestId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", assetId=");
		sb.append(getAssetId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", requestedDate=");
		sb.append(getRequestedDate());
		sb.append(", assingedDate=");
		sb.append(getAssingedDate());
		sb.append(", bookedDate=");
		sb.append(getBookedDate());
		sb.append(", returnedDate=");
		sb.append(getReturnedDate());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", priority=");
		sb.append(getPriority());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.rivetlogic.assetmanagement.model.AssetRequest");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>uuid</column-name><column-value><![CDATA[");
		sb.append(getUuid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assetRequestId</column-name><column-value><![CDATA[");
		sb.append(getAssetRequestId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assetId</column-name><column-value><![CDATA[");
		sb.append(getAssetId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>requestedDate</column-name><column-value><![CDATA[");
		sb.append(getRequestedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>assingedDate</column-name><column-value><![CDATA[");
		sb.append(getAssingedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>bookedDate</column-name><column-value><![CDATA[");
		sb.append(getBookedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>returnedDate</column-name><column-value><![CDATA[");
		sb.append(getReturnedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>priority</column-name><column-value><![CDATA[");
		sb.append(getPriority());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = AssetRequest.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			AssetRequest.class
		};
	private String _uuid;
	private String _originalUuid;
	private long _assetRequestId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _assetId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private String _userName;
	private Date _requestedDate;
	private Date _assingedDate;
	private Date _bookedDate;
	private Date _returnedDate;
	private String _status;
	private String _priority;
	private String _description;
	private long _columnBitmask;
	private AssetRequest _escapedModel;
}