//**************************************************************
// CLASS: DBInterfcace
// PURPOSE:  IMPLEMENTS THE OPERATIONS FOR SQLITE DATABASE
//           THE SQLITE DATA BASE SAVES/RETRIEVES THE CURRENT STATE OF STEREN 
//           ROBOT ARM.
// FINAL PROJECT:  MOBILE APPLICATIONS 
// STUDENTS:   EDGAR ACOSTA / ABBAS ALSHAFAI
// MASTER OF SCIENCE IN ENGINEERING SOFTWARE ENGINEERING
// BS COMPUTER SCIENCE
// THE UNIVERSITY OF TEXAS AT EL PASO
// DATE: 04/18/2012
//***************************************************************


package edg.ROBOT.mob;

// LIBRARIES

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**Class:  DBInterface.
 * Implements SQlite data base Connector to store Steren Robotic Arm 
 *            STATE Values
 * @author edgar, abbas
 */

public class DBInterface 
{
	// TABLE STATE FIELDS VARIABLES
	public static final String KEY_IDSTATE = "_id";
	public static final String BASEM = "BaseM";
	public static final String SHOULDERM = "ShoulderM"; 
	public static final String ELBOWM = "ElbowM";
	public static final String WRISTM = "WristM";
	public static final String HANDM = "HandM";
	
	// DATABASE CREATION AND MANAGEMENT VARIABLES
	private static final String TAG = "DBInterface";
	private static final String DATABASE_NAME = "MotorState4";
	private static final String DATABASE_TBL_STATE = "State";
	private static final int    DATABASE_VERSION = 1;
	
	// CREATE TABLE SENTENCE
	private static final String CREATE_TBL_STATE = "create table State(_id integer primary key autoincrement, BaseM integer, ShoulderM integer, ElbowM integer, WristM integer, HandM integer);";
	
	private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
	
    /**Constructor DBConnector.
     * @param Context ctx
     * @return void 
     */
    public DBInterface(Context ctx)
    {
    	this.context = ctx;
    	DBHelper = new DatabaseHelper(context);
    }
    
    /** Embedded Class: DatabaseHelper.
     * @author Edgar Acosta
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
    	  /** Method: DatabaseHelper(Context context).
    	  *  Constructor.
    	  * @param context
    	  */
    	  DatabaseHelper(Context context)
    	  {
    		  super(context,DATABASE_NAME,null,DATABASE_VERSION);
    	  }
    	  /**Method onCreate().
    	  *  Create the data base and insert initial values
    	  */
    	  public void onCreate(SQLiteDatabase db)
    	  {
    	      try
    	      {
    	    	 db.execSQL(CREATE_TBL_STATE);
    	    	 db.execSQL("insert into " + DATABASE_TBL_STATE + "(" + BASEM + "," + SHOULDERM + "," + ELBOWM + "," + WRISTM + "," + HANDM +") values(" + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + ");"); 
    	      }
    	      catch(SQLException e)
    	      {
    	    	  e.printStackTrace();
    	      }
    	  }
    	  /** Method obUpgrade().
    	  *   Recreate the Data Base if necessary.
    	  */
    	  public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion)
    	  {
    		 db.execSQL("DROP TABLE IF EXIST CONTACTS");
    		 onCreate(db);
    	  }
    } //  End Class  DatabaseHelper
    
    /**Method open().
     * Open Data Base
     * @param  void
     * @return DBInterface
     */
   public DBInterface open() throws SQLException
   {
   		db = DBHelper.getWritableDatabase();
   		return this;
   }
   /** Method close().
    *  close data base
    *  @param void
    *  @return void
    */
   public void close()
   {
   		DBHelper.close();
   }
    
   /** Method:   updateBaseM.
    *  Purpose:  Update the Base Motor State Value
    *  @param:   rowId, newState
    *  @return:  long (-1 if fails)
    */
  	public boolean updateBaseM_State(long rowId, int newState)
  	{
  		ContentValues args = new ContentValues();
   		args.put(BASEM ,newState);
   		return db.update(DATABASE_TBL_STATE, args, KEY_IDSTATE+"="+rowId, null) > 0;
   	} 
  	
  	/** Method:   updateShoulderM().
     *  Purpose:  Update the Shoulder Motor State Value
     *  @param:   rowId, newState
     *  @return:  long (-1 if fails)
     */
    public boolean updateShoulderM_State(long rowId, int newState)
  	{
  		ContentValues args = new ContentValues();
   		args.put(SHOULDERM ,newState);
   		return db.update(DATABASE_TBL_STATE, args, KEY_IDSTATE+"="+rowId, null) > 0;
   	}
    /** Method:   updateElbowM().
     *  Purpose:  Update the Elbow Motor State Value
     *  @param:   rowId, newState
     *  @return:  long (-1 if fails)
     */
  	public boolean updateElbowM_State(long rowId, int newState)
  	{
  		ContentValues args = new ContentValues();
   		args.put(ELBOWM ,newState);
   		return db.update(DATABASE_TBL_STATE, args, KEY_IDSTATE+"="+rowId, null) > 0;
   	} 
  	
  	/** Method:   updateWristM().
     *  Purpose:  Update the Wrist Motor State Value
     *  @param:   rowId, newState
     *  @return:  long (-1 if fails)
     */
  	
  	public boolean updateWristM_State(long rowId, int newState)
  	{
  		ContentValues args = new ContentValues();
   		args.put(WRISTM ,newState);
   		return db.update(DATABASE_TBL_STATE, args, KEY_IDSTATE+"="+rowId, null) > 0;
   	} 
  	
  	/** Method:   updateHandM().
     *  Purpose:  Update the Hand Motor State Value
     *  @param:   rowId, newState
     *  @return:  long (-1 if fails)
     */
  	 	
  	public boolean updateHandM_State(long rowId, int newState)
  	{
  		ContentValues args = new ContentValues();
   		args.put(HANDM ,newState);
   		return db.update(DATABASE_TBL_STATE, args, KEY_IDSTATE+"="+rowId, null) > 0;
   	} 
    
	/**Method: Cursor getBaseM_State().
	 * get the Base Motor State from Data Base
	 * @param rowId
	 * @return Cursor
	 */
  	
  	public Cursor getBaseM_State(long rowId)
   	{
   		Cursor mcursor =  db.query(DATABASE_TBL_STATE, new String[] {BASEM},KEY_IDSTATE+"="+rowId, null, null, null, null);
   		if(mcursor != null)
   		{
   			mcursor.moveToFirst();
   		}
   		return mcursor;
   	}
  	
  	/**Method: Cursor getShoulderM_State().
	 * get the Shoulder Motor State from Data Base
	 * @param rowId
	 * @return Cursor
	 */
  	
  	public Cursor getShoulderM_State(long rowId)
   	{
   		Cursor mcursor =  db.query(DATABASE_TBL_STATE, new String[] {SHOULDERM},KEY_IDSTATE+"="+rowId, null, null, null, null);
   		if(mcursor != null)
   		{
   			mcursor.moveToFirst();
   		}
   		return mcursor;
   	}
  	/**Method: Cursor getElbowM_State().
	 * get the Elbow Motor State from Data Base
	 * @param rowId
	 * @return Cursor
	 */
  	public Cursor getElbowM_State(long rowId)
   	{
   		Cursor mcursor =  db.query(DATABASE_TBL_STATE, new String[] {ELBOWM},KEY_IDSTATE+"="+rowId, null, null, null, null);
   		if(mcursor != null)
   		{
   			mcursor.moveToFirst();
   		}
   		return mcursor;
   	}
  	
  	/**Method: Cursor getWristM_State().
	 * get the Wrist Motor State from Data Base
	 * @param rowId
	 * @return Cursor
	 */
  	public Cursor getWristM_State(long rowId)
   	{
   		Cursor mcursor =  db.query(DATABASE_TBL_STATE, new String[] {WRISTM},KEY_IDSTATE+"="+rowId, null, null, null, null);
   		if(mcursor != null)
   		{
   			mcursor.moveToFirst();
   		}
   		return mcursor;
   	}
  	
  	/**Method: Cursor getHandM_State().
	 * get the Hand Motor State from Data Base
	 * @param rowId
	 * @return Cursor
	 */
  	public Cursor getHandM_State(long rowId)
   	{
   		Cursor mcursor =  db.query(DATABASE_TBL_STATE, new String[] {HANDM},KEY_IDSTATE+"="+rowId, null, null, null, null);
   		if(mcursor != null)
   		{
   			mcursor.moveToFirst();
   		}
   		return mcursor;
   	}
  	/**Method: Cursor getCurrent_State().
	 * get the Current State of the Steren Motor (return all 5 motor state)
	 * @param 
	 * @return Cursor
	 */
	public Cursor getCurrent_State()
   	{
   		return  db.query(DATABASE_TBL_STATE, new String[] {KEY_IDSTATE,BASEM,SHOULDERM,ELBOWM,WRISTM,HANDM},null, null, null, null, null);
   	}
	
	/**Method  ReZero().
	 * Reset the state of Data Base to Zero (All States to Zero) RESET STATE
	 * @param void
	 * @return boolean
	 */
	public boolean ReZero(long rowId)
  	{
		final int intZero = 0;
  		ContentValues args = new ContentValues();
  		args.put(BASEM ,intZero);
  		args.put(SHOULDERM,intZero);
  		args.put(ELBOWM, intZero);
  		args.put(WRISTM ,intZero);
  		args.put(HANDM , intZero);
   		return db.update(DATABASE_TBL_STATE, args, KEY_IDSTATE+"="+rowId, null) > 0;
   	} 
	
	
} // END CLASS

//*****************************************************************************
//****************************************************************************
//C H A N G E   L O G
//*****************************************************************************
//04/18/2012    Edgar Acosta/ Abbas Alshafai   Initial Release

