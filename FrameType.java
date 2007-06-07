


/**
 *  
 * FrameType defines what kinds of Frame we are dealing with at the moment.
 * @author Ishwor Gurung
 * @version 0.2 - Fixed class
 *
 */
public interface FrameType {

	//OK, S2C. Acknowledge
	//Ok, continue or passed
	static int OK = 0;
	
	//Fail, S2C. Negative Acknowledgement
	//Failed, stop or impossible
	static int FAIL = 1;
	
	//S2C, Multiple frames will follow
	static int SEQUENCE = 2;
	/**
	 * Frame type error code converted from enums in libtpproto-cpp
	 * 
	 */
	class FTErrorCode {
		public static int   fec_Invalid = -1,
						    fec_ProtocolError = 0,
						    fec_FrameError = 1,
						    fec_PermUnavailable = 2,
						    fec_TempUnavailable = 3,
						    fec_NonExistant = 4,
						    fec_PermissionDenied = 5,
						    fec_Max = 6;
	}
	
	/**
	 * 
	 * Converted enums from libtpproto-cpp
	 * Different frame types
	 */
	
	class FT {
	
	
		//Probably not a good idea to create a class for each type of
		//frame type because of cost involved in creating new class each time!
		//NOTE - this is fixed now (16/5/07)
		 
		public static final int  ft02_Invalid = -1,
								 ft02_OK = 0,
								 ft02_Fail = 1,
								 ft02_Sequence = 2,
								 ft02_Connect = 3,
								 ft02_Login = 4,
								 ft02_Object_GetById = 5,
								 ft02_Object_GetByPos = 6,
								 ft02_Object = 7,
								 ft02_OrderDesc_Get = 8,
								 ft02_OrderDesc = 9,
								 ft02_Order_Get = 10,
								 ft02_Order = 11,
								 ft02_Order_Insert = 12,
								 ft02_Order_Remove = 13,
								 ft02_Time_Remaining_Get = 14,
								 ft02_Time_Remaining = 15,
								 ft02_Board_Get = 16,
								 ft02_Board = 17,
								 ft02_Message_Get = 18,
								 ft02_Message = 19,
								 ft02_Message_Post = 20,
								 ft02_Message_Remove = 21,
								 ft02_ResDesc_Get = 22,
								 ft02_ResDesc = 23,
								 ft03_Redirect = 24,
								 ft03_Features_Get = 25,
								 ft03_Features = 26,
								 ft03_Ping = 27,
								 ft03_ObjectIds_Get = 28,
								 ft03_ObjectIds_GetByPos = 29,
								 ft03_ObjectIds_GetByContainer = 30,
								 ft03_ObjectIds = 31,
								 ft03_OrderTypes_Get = 32,
								 ft03_OrderTypes = 33,
								 ft03_Order_Probe = 34,
								 ft03_BoardIds_Get = 35,
								 ft03_BoardIds = 36,
								 ft03_ResourceTypes_Get = 37,
								 ft03_ResourceTypes = 38,
								 ft03_Player_Get = 39,
								 ft03_Player = 40,
								 ft03_Category_Get = 41,
								 ft03_Category = 42,
								 ft03_Category_Add = 43,
								 ft03_Category_Remove = 44,
								 ft03_CategoryIds_Get = 45,
								 ft03_CategoryIds = 46,
								 ft03_Design_Get = 47,
								 ft03_Design = 48,
								 ft03_Design_Add = 49,
								 ft03_Design_Modify = 50,
								 ft03_Design_Remove = 51,
								 ft03_DesignIds_Get = 52,
								 ft03_DesignIds = 53,
								 ft03_Component_Get = 54,
								 ft03_Component = 55,
								 ft03_ComponentIds_Get = 56,
								 ft03_ComponentIds = 57,
								 ft03_Property_Get = 58,
								 ft03_Property = 59,
								 ft03_PropertyIds_Get = 60,
								 ft03_PropertyIds = 61,
								 ft03_Account_Create = 62,
								 ft02_Max = 63;	
	}
	
}
