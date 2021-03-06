package com.android.framework.ble.util;

import java.util.HashMap;
import java.util.Map;

/**
* - http://developer.nokia.com/community/wiki/Bluetooth_Services_for_Windows_Phone
*/

public class GattAttributeUtil {
	
	public static final String BASE_GUID = "00000001-0000-1000-8000-00805f9b34fb";
	public static final String SERVICE_DISCOVERY_PROTOCOL_SDP = "00000002-0000-1000-8000-00805f9b34fb";
	public static final String USER_DATAGRAM_PROTOCOL_UDP = "00000003-0000-1000-8000-00805f9b34fb";
	public static final String RADIO_FREQUENCY_COMMUNICATION_PROTOCOL_RFCOMM = "00000004-0000-1000-8000-00805f9b34fb";
	public static final String TCP = "00000005-0000-1000-8000-00805f9b34fb";
	public static final String TCSBIN = "00000006-0000-1000-8000-00805f9b34fb";
	public static final String TCSAT = "00000008-0000-1000-8000-00805f9b34fb";
	public static final String OBJECT_EXCHANGE_PROTOCOL_OBEX = "00000009-0000-1000-8000-00805f9b34fb";
	public static final String IP = "0000000a-0000-1000-8000-00805f9b34fb";
	public static final String FTP = "0000000c-0000-1000-8000-00805f9b34fb";
	public static final String HTTP = "0000000e-0000-1000-8000-00805f9b34fb";
	public static final String WSP = "0000000f-0000-1000-8000-00805f9b34fb";
	public static final String BNEP_SVC = "00000010-0000-1000-8000-00805f9b34fb";
	public static final String UPNP_PROTOCOL = "00000011-0000-1000-8000-00805f9b34fb";
	public static final String HIDP = "00000012-0000-1000-8000-00805f9b34fb";
	public static final String HARDCOPY_CONTROL_CHANNEL_PROTOCOL = "00000014-0000-1000-8000-00805f9b34fb";
	public static final String HARDCOPY_DATA_CHANNEL_PROTOCOL = "00000016-0000-1000-8000-00805f9b34fb";
	public static final String HARDCOPY_NOTIFICATION_PROTOCOL = "00000017-0000-1000-8000-00805f9b34fb";
	public static final String VCTP_PROTOCOL = "00000019-0000-1000-8000-00805f9b34fb";
	public static final String VDTP_PROTOCOL = "0000001b-0000-1000-8000-00805f9b34fb";
	public static final String CMPT_PROTOCOL = "0000001d-0000-1000-8000-00805f9b34fb";
	public static final String UDI_C_PLANE_PROTOCOL = "0000001e-0000-1000-8000-00805f9b34fb";
	public static final String MCAP_CONTROL_CHANNEL = "0000001f-0000-1000-8000-00805f9b34fb";
	public static final String MCAP_DATA_CHANNEL = "00000100-0000-1000-8000-00805f9b34fb";
	public static final String L2CAP = "00001000-0000-1000-8000-00805f9b34fb";
	public static final String SERVICE_DISCOVERY_SERVER = "00001001-0000-1000-8000-00805f9b34fb";
	public static final String BROWSE_GROUP_DESCRIPTOR = "00001002-0000-1000-8000-00805f9b34fb";
	public static final String PUBLIC_BROWSE_GROUP = "00001101-0000-1000-8000-00805f9b34fb";
	public static final String SPP = "00001102-0000-1000-8000-00805f9b34fb";
	public static final String LAN_ACCESS_USING_PPP = "00001103-0000-1000-8000-00805f9b34fb";
	public static final String DUN_GW = "00001104-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_SYNC = "00001105-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_OBJECT_PUSH = "00001106-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_FILE_TRANSFER = "00001107-0000-1000-8000-00805f9b34fb";
	public static final String IRMC_SYNC_COMMAND = "00001108-0000-1000-8000-00805f9b34fb";
	public static final String HSP_HS = "00001109-0000-1000-8000-00805f9b34fb";
	public static final String CORDLESS_TELEPHONY = "0000110a-0000-1000-8000-00805f9b34fb";
	public static final String AUDIO_SOURCE = "0000110b-0000-1000-8000-00805f9b34fb";
	public static final String AUDIO_SINK = "0000110c-0000-1000-8000-00805f9b34fb";
	public static final String AV_REMOTE_CONTROL_TARGET = "0000110d-0000-1000-8000-00805f9b34fb";
	public static final String ADVANCED_AUDIO = "0000110e-0000-1000-8000-00805f9b34fb";
	public static final String AVRCP_REMOTE = "0000110f-0000-1000-8000-00805f9b34fb";
	public static final String VIDEO_CONFERENCING = "00001110-0000-1000-8000-00805f9b34fb";
	public static final String INTERCOM = "00001111-0000-1000-8000-00805f9b34fb";
	public static final String FAX = "00001112-0000-1000-8000-00805f9b34fb";
	public static final String HEADSET_PROFILE_HSP_AUDIO_GATEWAY = "00001113-0000-1000-8000-00805f9b34fb";
	public static final String WAP = "00001114-0000-1000-8000-00805f9b34fb";
	public static final String WAP_CLIENT = "00001115-0000-1000-8000-00805f9b34fb";
	public static final String PANU = "00001116-0000-1000-8000-00805f9b34fb";
	public static final String NAP = "00001117-0000-1000-8000-00805f9b34fb";
	public static final String GN = "00001118-0000-1000-8000-00805f9b34fb";
	public static final String DIRECT_PRINTING = "00001119-0000-1000-8000-00805f9b34fb";
	public static final String REFERENCE_PRINTING = "0000111a-0000-1000-8000-00805f9b34fb";
	public static final String IMAGING = "0000111b-0000-1000-8000-00805f9b34fb";
	public static final String IMAGING_RESPONDER = "0000111c-0000-1000-8000-00805f9b34fb";
	public static final String IMAGING_AUTOMATIC_ARCHIVE = "0000111d-0000-1000-8000-00805f9b34fb";
	public static final String IMAGING_REFERENCE_OBJECTS = "0000111e-0000-1000-8000-00805f9b34fb";
	public static final String HANDS_FREE_PROFILE_HFP = "0000111f-0000-1000-8000-00805f9b34fb";
	public static final String HANDS_FREE_PROFILE_HFP_AUDIO_GATEWAY = "00001120-0000-1000-8000-00805f9b34fb";
	public static final String DIRECT_PRINTING_REFERENCE_OBJECTS = "00001121-0000-1000-8000-00805f9b34fb";
	public static final String REFLECTED_UI = "00001122-0000-1000-8000-00805f9b34fb";
	public static final String BASIC_PRINTING = "00001123-0000-1000-8000-00805f9b34fb";
	public static final String PRINTING_STATUS = "00001124-0000-1000-8000-00805f9b34fb";
	public static final String HID = "00001125-0000-1000-8000-00805f9b34fb";
	public static final String HARDCOPY_CABLE_REPLACEMENT = "00001126-0000-1000-8000-00805f9b34fb";
	public static final String HCR_PRINT = "00001127-0000-1000-8000-00805f9b34fb";
	public static final String HCR_SCAN = "00001128-0000-1000-8000-00805f9b34fb";
	public static final String COMMON_ISDN_ACCESS = "00001129-0000-1000-8000-00805f9b34fb";
	public static final String VIDEO_CONFERENCING_GATEWAY = "0000112a-0000-1000-8000-00805f9b34fb";
	public static final String UDIMT = "0000112b-0000-1000-8000-00805f9b34fb";
	public static final String UDITA = "0000112c-0000-1000-8000-00805f9b34fb";
	public static final String AUDIO_VIDEO = "0000112d-0000-1000-8000-00805f9b34fb";
	public static final String SIM_ACCESS = "0000112e-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_PCE = "0000112f-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_PSE = "00001130-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_PBAP = "00001132-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_MAS = "00001133-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_MNS = "00001134-0000-1000-8000-00805f9b34fb";
	public static final String OBEX_MAP = "00001200-0000-1000-8000-00805f9b34fb";
	public static final String PNP = "00001201-0000-1000-8000-00805f9b34fb";
	public static final String GENERIC_NETWORKING = "00001202-0000-1000-8000-00805f9b34fb";
	public static final String GENERIC_FILE_TRANSFER = "00001203-0000-1000-8000-00805f9b34fb";
	public static final String GENERIC_AUDIO = "00001204-0000-1000-8000-00805f9b34fb";
	public static final String GENERIC_TELEPHONY = "00001205-0000-1000-8000-00805f9b34fb";
	public static final String UPNP = "00001206-0000-1000-8000-00805f9b34fb";
	public static final String UPNP_IP = "00001300-0000-1000-8000-00805f9b34fb";
	public static final String ESDP_UPNP_IP_PAN = "00001301-0000-1000-8000-00805f9b34fb";
	public static final String ESDP_UPNP_IP_LAP = "00001302-0000-1000-8000-00805f9b34fb";
	public static final String ESDP_UPNP_L2CAP = "00001303-0000-1000-8000-00805f9b34fb";
	public static final String VIDEO_DISTRIBUTION_PROFILE_VDP_SOURCE = "00001304-0000-1000-8000-00805f9b34fb";
	public static final String VIDEO_DISTRIBUTION_PROFILE_VDP_SINK = "00001305-0000-1000-8000-00805f9b34fb";
	public static final String VIDEO_DISTRIBUTION_PROFILE_VDP = "00001400-0000-1000-8000-00805f9b34fb";
	public static final String HEALTH_DEVICE_PROFILE_HDP = "00001401-0000-1000-8000-00805f9b34fb";
	public static final String HEALTH_DEVICE_PROFILE_HDP_SOURCE = "00001402-0000-1000-8000-00805f9b34fb";
	public static final String HEALTH_DEVICE_PROFILE_HDP_SINK = "00001800-0000-1000-8000-00805f9b34fb";
	public static final String GAP = "00001801-0000-1000-8000-00805f9b34fb";
	public static final String GATT = "00001802-0000-1000-8000-00805f9b34fb";
	public static final String IMMEDIATE_ALERT = "00001803-0000-1000-8000-00805f9b34fb";
	public static final String LINK_LOSS = "00001804-0000-1000-8000-00805f9b34fb";
	public static final String TX_POWER = "00001809-0000-1000-8000-00805f9b34fb";
	public static final String HEALTH_THERMOMETER = "0000180a-0000-1000-8000-00805f9b34fb";
	public static final String DEVICE_INFORMATION = "0000180d-0000-1000-8000-00805f9b34fb";
	public static final String HEART_RATE = "00001816-0000-1000-8000-00805f9b34fb";
	public static final String CYCLING_SC = "00002902-0000-1000-8000-00805f9b34fb";
	public static final String CLIENT_CHARACTERISTIC_CONFIG = "00002a00-0000-1000-8000-00805f9b34fb";
	public static final String DEVICE_NAME = "00002a01-0000-1000-8000-00805f9b34fb";
	public static final String APPEARANCE = "00002a02-0000-1000-8000-00805f9b34fb";
	public static final String PERIPHERAL_PRIVACY_FLAG = "00002a03-0000-1000-8000-00805f9b34fb";
	public static final String RECONNECTION_ADDRESS = "00002a04-0000-1000-8000-00805f9b34fb";
	public static final String PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS = "00002a05-0000-1000-8000-00805f9b34fb";
	public static final String SERVICE_CHANGED = "00002a06-0000-1000-8000-00805f9b34fb";
	public static final String ALERT_LEVEL = "00002a07-0000-1000-8000-00805f9b34fb";
	public static final String TX_POWER_LEVEL = "00002a08-0000-1000-8000-00805f9b34fb";
	public static final String DATE_TIME = "00002a09-0000-1000-8000-00805f9b34fb";
	public static final String DAY_OF_WEEK = "00002a0a-0000-1000-8000-00805f9b34fb";
	public static final String DAY_DATE_TIME = "00002a0c-0000-1000-8000-00805f9b34fb";
	public static final String EXACT_TIME_256 = "00002a0d-0000-1000-8000-00805f9b34fb";
	public static final String DST_OFFSET = "00002a0e-0000-1000-8000-00805f9b34fb";
	public static final String TIME_ZONE = "00002a0f-0000-1000-8000-00805f9b34fb";
	public static final String LOCAL_TIME_INFORMATION = "00002a11-0000-1000-8000-00805f9b34fb";
	public static final String TIME_WITH_DST = "00002a12-0000-1000-8000-00805f9b34fb";
	public static final String TIME_ACCURACY = "00002a13-0000-1000-8000-00805f9b34fb";
	public static final String TIME_SOURCE = "00002a14-0000-1000-8000-00805f9b34fb";
	public static final String REFERENCE_TIME_INFORMATION = "00002a16-0000-1000-8000-00805f9b34fb";
	public static final String TIME_UPDATE_CONTROL_POINT = "00002a17-0000-1000-8000-00805f9b34fb";
	public static final String TIME_UPDATE_STATE = "00002a1c-0000-1000-8000-00805f9b34fb";
	public static final String TEMPERATURE_MEASUREMENT = "00002a1d-0000-1000-8000-00805f9b34fb";
	public static final String TEMPERATURE_TYPE = "00002a1e-0000-1000-8000-00805f9b34fb";
	public static final String INTERMEDIATE_TEMPERATURE = "00002a21-0000-1000-8000-00805f9b34fb";
	public static final String MEASUREMENT_INTERVAL = "00002a23-0000-1000-8000-00805f9b34fb";
	public static final String SYSTEM_ID = "00002a24-0000-1000-8000-00805f9b34fb";
	public static final String MODEL_NUMBER_STRING = "00002a25-0000-1000-8000-00805f9b34fb";
	public static final String SERIAL_NUMBER_STRING = "00002a26-0000-1000-8000-00805f9b34fb";
	public static final String FIRMWARE_REVISION_STRING = "00002a27-0000-1000-8000-00805f9b34fb";
	public static final String HARDWARE_REVISION_STRING = "00002a28-0000-1000-8000-00805f9b34fb";
	public static final String SOFTWARE_REVISION_STRING = "00002a29-0000-1000-8000-00805f9b34fb";
	public static final String MANUFACTURER_NAME_STRING = "00002a2a-0000-1000-8000-00805f9b34fb";
	public static final String IEEE_1107320601_REGULATORY = "00002a2b-0000-1000-8000-00805f9b34fb";
	public static final String CURRENT_TIME = "00002a35-0000-1000-8000-00805f9b34fb";
	public static final String BLOOD_PRESSURE_MEASUREMENT = "00002a36-0000-1000-8000-00805f9b34fb";
	public static final String INTERMEDIATE_CUFF_PRESSURE = "00002a37-0000-1000-8000-00805f9b34fb";
	public static final String HEART_RATE_MEASUREMENT = "00002a38-0000-1000-8000-00805f9b34fb";
	public static final String BODY_SENSOR_LOCATION = "00002a39-0000-1000-8000-00805f9b34fb";
	public static final String HEART_RATE_CONTROL_POINT = "00002a3f-0000-1000-8000-00805f9b34fb";
	public static final String ALERT_STATUS = "00002a40-0000-1000-8000-00805f9b34fb";
	public static final String RINGER_CONTROL_POINT = "00002a41-0000-1000-8000-00805f9b34fb";
	public static final String RINGER_SETTING = "00002a42-0000-1000-8000-00805f9b34fb";
	public static final String ALERT_CATEGORY_ID_BIT_MASK = "00002a43-0000-1000-8000-00805f9b34fb";
	public static final String ALERT_CATEGORY_ID = "00002a44-0000-1000-8000-00805f9b34fb";
	public static final String ALERT_NOTIFICATION_CONTROL_POINT = "00002a45-0000-1000-8000-00805f9b34fb";
	public static final String UNREAD_ALERT_STATUS = "00002a46-0000-1000-8000-00805f9b34fb";
	public static final String NEW_ALERT = "00002a47-0000-1000-8000-00805f9b34fb";
	public static final String SUPPORTED_NEW_ALERT_CATEGORY = "00002a48-0000-1000-8000-00805f9b34fb";
	public static final String SUPPORTED_UNREAD_ALERT_CATEGORY = "00002a49-0000-1000-8000-00805f9b34fb";
	public static final String BLOOD_PRESSURE_FEATURE = "00002a50-0000-1000-8000-00805f9b34fb";
	public static final String PNPID = "00002a55-0000-1000-8000-00805f9b34fb";
	public static final String SC_CONTROL_POINT = "00002a5b-0000-1000-8000-00805f9b34fb";
	public static final String CSC_MEASUREMENT = "00002a5c-0000-1000-8000-00805f9b34fb";
	public static final String CSC_FEATURE = "00002a5d-0000-1000-8000-00805f9b34fb";
	public static final String SENSOR_LOCATION = "831c4071-7bc8-4a9c-a01c-15df25a4adbc";
	public static final String ACTIVESYNC = "831c4071-7bc8-4a9c-a01c-15df25a4adbc";
	public static final String ESTIMOTE_SERVICE = "b9403000-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_UUID = "b9403003-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_MAJOR = "b9403001-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_MINOR = "b9403002-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_BATTERY = "b9403041-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_TEMPERATURE = "b9403021-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_POWER = "b9403011-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_ADVERTISING_INTERVAL = "b9403012-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_VERSION_SERVICE = "b9404000-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_SOFTWARE_VERSION = "b9404001-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_HARDWARE_VERSION = "b9404002-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_AUTHENTICATION_SERVICE = "b9402000-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_ADVERTISING_SEED = "b9402001-f5f8-466e-aff9-25556b57fe6d";
	public static final String ESTIMOTE_ADVERTISING_VECTOR = "b9402002-f5f8-466e-aff9-25556b57fe6d";
	
	private static Map<String, String> sGattAttributesMap = new HashMap<String, String>();
	
	public static void putUserGattAttribute(Map<String, String> userGattMap){
		
	}
	
	
	
}
