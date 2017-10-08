//************************************************************************
// CLASS: ManualControl
// PURPOSE:  IMPLEMENTS THE OPERATIONS FOR ROBOTIC ARM MANUAL CONTROL
//           WITH BUTTONS AND BLUETOOTH COMMUNICATION
// FINAL PROJECT:  MOBILE APPLICATIONS 
// STUDENTS:   EDGAR ACOSTA / ABBAS
// MASTER OF SCIENCE IN ENGINEERING SOFTWARE ENGINEERING
// BS COMPUTER SCIENCE
// THE UNIVERSITY OF TEXAS AT EL PASO
//************************************************************************

package edg.ROBOT.mob;

/* libraries */
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;     // test
import at.abraxas.amarino.Amarino; /* AMARINO LIBRARY BLUETOOTH */


/**Class: ManualControl.
 * Implements the GUI and Methods for Robotic Arm Manual Control
 * @author: Edgar Acosta
 */
public class ManualControl extends Activity
{
	private static final String TAG = "ManualControl";
	
	Button cmdBaseLeft;
	Button cmdBaseRight;
	Button cmdShoulderUp;
	Button cmdShoulderDown;
	Button cmdElbowUp;
	Button cmdElbowDown;
	Button cmdWristUp;
	Button cmdWristDown;
	Button cmdHandOpen;
	Button cmdHandClose;
	Button cmdReturn;
	
	TextView base;
	TextView shoulder;
	TextView elbow;
	TextView hand;
	TextView wrist;
	TextView back;
	
	Typeface font;
	
	private static final String DEVICE_ADDRESS = "00:06:66:08:5E:F1";
	
	 DBInterface DB = new DBInterface(this);  //Data Base Object
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualcontrol);
     
        // CONNECTING USING BLUETOOTH TO ARDUINO
        Amarino.connect(this, DEVICE_ADDRESS);
               
        cmdReturn = (Button)findViewById(R.id.returnBtn);
        cmdBaseLeft = (Button)findViewById(R.id.cmdBaseLeft);
        cmdBaseRight = (Button)findViewById(R.id.cmdBaseRight);
        cmdShoulderUp = (Button)findViewById(R.id.cmdShoulderUp);
    	cmdShoulderDown = (Button)findViewById(R.id.cmdShoulderDown);
    	cmdElbowUp = (Button)findViewById(R.id.cmdElbowUp);
    	cmdElbowDown = (Button)findViewById(R.id.cmdElbowDown);
    	cmdWristUp = (Button)findViewById(R.id.cmdWristUp);
    	cmdWristDown = (Button)findViewById(R.id.cmdWristDown);
    	cmdHandOpen = (Button)findViewById(R.id.cmdHandOpen);
    	cmdHandClose = (Button)findViewById(R.id.cmdHandClose);
    	
    	font = Typeface.createFromAsset(getAssets(), "moderno.ttf");
    	
    	base = (TextView) findViewById(R.id.base);
    	base.setTypeface(font);

    	wrist = (TextView) findViewById(R.id.wrist);
    	wrist.setTypeface(font);
    	
    	shoulder = (TextView) findViewById(R.id.shoulder);
    	shoulder.setTypeface(font);
    	
    	elbow = (TextView) findViewById(R.id.elbow);
    	elbow.setTypeface(font);
    	
    	hand = (TextView) findViewById(R.id.hand);
    	hand.setTypeface(font);
    	
    	back = (TextView) findViewById(R.id.backM);
    	back.setTypeface(font);
    	
        /**Method:  cmdBaseLeft.setOnClickListener.
         * Send Signal to Base left Arm Motor
         */
        cmdBaseLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
				   // GET CURRENT STATE OF BASE MOTOR
				   int intState = 0;
				   DB.open();   // Open Motor State Data Base
				   Cursor C = DB.getBaseM_State(1);
				   C.moveToFirst();
				   intState = Integer.parseInt(C.getString(0));   
				   if (intState <  4)
				   {
				        Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'a', false);
				        intState++;
				        Thread.sleep(1000);
				        Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				        DB.updateBaseM_State(1, intState);  // Update Base Motor State
				   }
				   DB.close();  // Close Data Base
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
	     	}
		});
        
        /**Method: cmdBaseRight.setOnClickListener
         * Send Signal to Base Right Arm motor
         */
        cmdBaseRight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					   // GET CURRENT STATE OF BASE MOTOR
					   int intState = 0;
					   DB.open();
					   Cursor C = DB.getBaseM_State(1);
					   C.moveToFirst();
					   intState = Integer.parseInt(C.getString(0));  
					   
					   if (intState > -4)
					   {
				            Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'b', false);
				            intState--;
				            Thread.sleep(1000);
				            Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				            DB.updateBaseM_State(1, intState);  // Update Base Motor State
					   }
					   DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
			}
		});
        
        /**Method: cmdShoulderUp.setOnClickListener.
         * Send Signal to Shoulder Up Arm motor
         */
        cmdShoulderUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					
				   // GET CURRENT STATE OF SHOULDER MOTOR
				   int intState = 0;
				   DB.open();
				   Cursor C = DB.getShoulderM_State(1);  
				   C.moveToFirst();
				   intState = Integer.parseInt(C.getString(0));
				   if(intState <  4 )
				   {
				       Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'd', false);
				       intState++;
				       Thread.sleep(1000);
				       Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				       DB.updateShoulderM_State(1,intState);  // Update Shoulder Motor State
				   }
				   DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				
			}
		});
        /**Method: cmdShoulderDown.setOnClickListener.
         * Send Signal to Shoulder Down Arm motor
         */
    	cmdShoulderDown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				try
				{
					
					 // GET CURRENT STATE OF SHOULDER MOTOR
					 int intState = 0;
					 DB.open();
					 Cursor C = DB.getShoulderM_State(1);  
					 C.moveToFirst();
					 intState = Integer.parseInt(C.getString(0));
					 if(intState > 0 )
					 {
					      Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'c', false);
					      intState--;
					      Thread.sleep(1000);
				          Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				          DB.updateShoulderM_State(1,intState);  // Update Shoulder Motor State
					 }
					 DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
			}
		});
        
    	/**Method: cmdElbowUp.setOnClickListener
         * Send Signal to Elbow Up Arm motor
         */
    	cmdElbowUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					// GET CURRENT STATE OF SHOULDER MOTOR
					 int intState = 0;
					 DB.open();
					 Cursor C = DB.getElbowM_State(1);  
					 C.moveToFirst();
					 intState = Integer.parseInt(C.getString(0));
					 
					 if(intState < 4 )
					 {
					  	   Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'e', false);
					  	   intState++;
					  	   Thread.sleep(1000);
				           Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				           DB.updateElbowM_State(1, intState);  // Update Elbow Motor State
					 }
					 DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
			}
		});
    	
    	/**Method: cmdElbowDown.setOnClickListener
         * Send Signal to Elbow Down Arm motor
         */
    	
    	cmdElbowDown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					// GET CURRENT STATE OF SHOULDER MOTOR
					 int intState = 0;
					 DB.open();
					 Cursor C = DB.getElbowM_State(1);  
					 C.moveToFirst();
					 intState = Integer.parseInt(C.getString(0));
					
					 if(intState > 0 )
					 {
					 	 Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'f', false);
					 	 intState--;
					     Thread.sleep(1000);
				         Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				         DB.updateElbowM_State(1, intState);  // Update Elbow Motor State
					 }
					 DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
			}
		});
    	
    	/**Method: cmdWristUp.setOnClickListener
         * Send Signal to Wrist Up Arm motor
         */
    	cmdWristUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					
					// GET CURRENT STATE OF SHOULDER MOTOR
					 int intState = 0;
					 DB.open();
					 Cursor C = DB.getWristM_State(1);
					 C.moveToFirst();
					 intState = Integer.parseInt(C.getString(0));
					 
					 if(intState < 5)
					 {
				        Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'g', false);
				        intState++;
				        Thread.sleep(1000);
				        Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				        DB.updateWristM_State(1, intState);   //UPDATE WRIST MOTOR STATE
					 }
					 DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
			}
		});
    	
    	/**Method: cmdWristDown.setOnClickListener
         * Send Signal to Wrist Down Arm motor
         */
    	
    	cmdWristDown.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					// GET CURRENT STATE OF SHOULDER MOTOR
					 int intState = 0;
					 DB.open();
					 Cursor C = DB.getWristM_State(1);  
					 C.moveToFirst();
					 intState = Integer.parseInt(C.getString(0));
					 
					 if(intState > 0)
					 {
				          Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'h', false);
				          intState--;
				          Thread.sleep(1000);
				          Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				          DB.updateWristM_State(1, intState);  // UPDATE WRIST MOTOR STATE
					 }
					 DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
			}
		}); 
    	
    	/**Method: cmdHandOpen.setOnClickListener.
         * Send Signal to Hand Open Arm motor
         */
    	cmdHandOpen.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					
					 // GET CURRENT STATE OF SHOULDER MOTOR
					 int intState = 0;
					 DB.open();
					 Cursor C = DB.getHandM_State(1);  
					 C.moveToFirst();
					 intState = Integer.parseInt(C.getString(0));
					 
					 if (intState < 2)
					 {
					 	 Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'i', false);
					 	 intState++;
					 	 Thread.sleep(600);
				         Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				         DB.updateHandM_State(1, intState);  // UPDATE HAND MOTOR STATE
					 }
					 DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
			}
		});
    	
    	/**Method: cmdHandClose.setOnClickListener.
         * Send Signal to Hand Close Arm motor
         */
    	cmdHandClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					
					// GET CURRENT STATE OF SHOULDER MOTOR
					 int intState = 0;
					 DB.open();
					 Cursor C = DB.getHandM_State(1);  
					 C.moveToFirst();
					 intState = Integer.parseInt(C.getString(0));
					 
					 if(intState > 0 )
					 {
				          Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 'j', false);
				          intState--;
				          Thread.sleep(600);
				          Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
				          DB.updateHandM_State(1, intState);  // UPDATE HAND MOTOR STATE
					 }
					 DB.close();
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);
					DB.close();
				}
				Amarino.sendDataToArduino(ManualControl.this, DEVICE_ADDRESS, 's', false);			
			}
		});

 
    	/**Method:  cmdReturn.setOnClickListener.
         * Return to Main Activity
         */
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				DB.close();  // Close Data Base
				finish();	
			}
		});    	
    	
    	/**Method:  cmdReturn.setOnClickListener.
         * Return to Main Activity
         */
        cmdReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				DB.close();  // Close Data Base
				finish();	
			}
		});
       
    }  // End onCreate()
    
    /**Method:  onStop() Activity Event
     * Disconnect from Data Base
     */
    public void onStop()
    {
    	super.onStop();
    	DB.close(); 
    	//Amarino.disconnect(this, DEVICE_ADDRESS);
    }
}    // END CLASS


//*****************************************************************************
//****************************************************************************
//C H A N G E   L O G
//*****************************************************************************
//04/15/2012    Edgar Acosta/ Abbas Alshafai   Initial Release
