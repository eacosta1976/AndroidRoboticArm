//************************************************************************
// CLASS: ManualControl
// PURPOSE:  IMPLEMENTS THE RESET SEQUENCE FOR THE ROBOT AFTER THIS SEQUENCE
//           IS EXECUTED THE ROBOT IS IN RESET POSITION 
// FINAL PROJECT:  MOBILE APPLICATIONS 
// STUDENTS:   EDGAR ACOSTA / ABBAS
// MASTER OF SCIENCE IN ENGINEERING SOFTWARE ENGINEERING
// BS COMPUTER SCIENCE
// THE UNIVERSITY OF TEXAS AT EL PASO
//************************************************************************
package edg.ROBOT.mob;

//LIBRARIES
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.database.Cursor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Handler;
import android.widget.Toast;
import at.abraxas.amarino.Amarino; /* AMARINO LIBRARY BLUETOOTH */

/**Class: ResetState.
 *  Implements the a Reset Sequence for the Robot Arm 
 * @author edgar
 */

public class ResetState extends Activity
{
     DBInterface DB = new DBInterface(this);  //Data Base Object
     private Handler mHandler = new Handler(); 
     private static final String TAG = "ResetState";
     private static final String DEVICE_ADDRESS = "00:06:66:08:5E:F1";
     
	 // widgets
	 Button cmdReset;
     Button cmdReturn;
	 TextView txtBaseMotor;
	 TextView txtShoulderMotor;
	 TextView txtElbowMotor;
	 TextView txtWristMotor;
	 TextView txtHandMotor;
	 TextView resetTxt;
	 TextView returnTxt;
	 Typeface font;
	 
	 int intBase = 0;
	 int intShoulder = 0;
	 int intElbow = 0;
	 int intWrist = 0;
	 int intHand = 0;
	 boolean boolStartReset = false; 
	 boolean AllReset = false;
	 
	 /**Method onCreate()
	  * 
	  */
	 public void onCreate(Bundle savedInstanceState)
	 {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.resetstate);
	     
	     font = Typeface.createFromAsset(getAssets(), "moderno.ttf");
	     
	     cmdReset = (Button)findViewById(R.id.cmdResetRobot);
	     cmdReturn = (Button)findViewById(R.id.cmdReturnReset);
	     txtBaseMotor = (TextView)findViewById(R.id.txtResetBaseMotor);
		 txtShoulderMotor = (TextView)findViewById(R.id.txtResetShoulderMotor);
		 txtElbowMotor = (TextView)findViewById(R.id.txtResetElbowMotor);
		 txtWristMotor = (TextView)findViewById(R.id.txtResetWristMotor);
		 txtHandMotor = (TextView)findViewById(R.id.txtResetHandMotor);
		 
		 resetTxt = (TextView) findViewById(R.id.resetTxt);
		 resetTxt.setTypeface(font);

		 returnTxt = (TextView) findViewById(R.id.returnTxt);
		 returnTxt.setTypeface(font);		 
		 
		 
		// CONNECTING USING BLUETOOTH TO ARDUINO
	     Amarino.connect(this, DEVICE_ADDRESS);
	     
	     // GET CURRENT STATE FROM DATA BASE 
		 DB.open();
		 Cursor B = DB.getCurrent_State();
		 B.moveToFirst();
		
		 
		 intBase = Integer.parseInt(B.getString(1));
		 intShoulder = Integer.parseInt(B.getString(2));
		 intElbow = Integer.parseInt(B.getString(3));
		 intWrist = Integer.parseInt(B.getString(4));
		 intHand = Integer.parseInt(B.getString(5));
		 
		 txtBaseMotor.setText(Integer.toString(intBase)); 
		 txtShoulderMotor.setText(Integer.toString(intShoulder));
		 txtElbowMotor.setText(Integer.toString(intElbow));
		 txtWristMotor.setText(Integer.toString(intWrist));
		 txtHandMotor.setText(Integer.toString(intHand));
		 
		 DB.close(); 

		 
		 /**Method:  cmdReset.setOnClickListener.
		  * Reset the state of the Robot
		  */
		 
		 resetTxt.setOnClickListener(new View.OnClickListener()
		 {
			 
			@Override
			public void onClick(View v)
			{
				 mHandler.removeCallbacks(Reset_Robot_State); 
		         mHandler.post(Reset_Robot_State);
			}
		});  // END EVENT
		 
		 
		 /**Method:  cmdReset.setOnClickListener.
		  * Reset the state of the Robot
		  */
		 
		 cmdReset.setOnClickListener(new View.OnClickListener()
		 {
			 
			@Override
			public void onClick(View v)
			{
				 mHandler.removeCallbacks(Reset_Robot_State); 
		         mHandler.post(Reset_Robot_State);
			}
		});  // END EVENT
		
		 /** cmdReturn.setOnClickListener
		  *  Return to Main Menu
		  */
		 returnTxt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				DB.close();  
				finish();				
			}
		});
		 
	 
		 /** cmdReturn.setOnClickListener
		  *  Return to Main Menu
		  */
		 cmdReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				DB.close();  
				finish();				
			}
		});

	 } // End OnCreate
	 
	 
	/**Thread Runnable Reset_Robot_State
	 *  Reset the Robot State and update Screen
	 */
	 private Runnable Reset_Robot_State = new Runnable()
	 { 
	 	    public void run()
	        { 
	        	//VERIFY BASE STATE
				 if (intBase == 0)
				 {
					 AllReset = true;
				 }
				 else if(intBase > 0)  //IF BASE IS IN LEFT SIDE (MOVE TO RIGHT TO RESET)
				 {
					 for (int i = intBase; i > 0; i--)
					 {
						 try
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 'b', false);
					         intBase--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					         DB.open();
					         DB.updateBaseM_State(1, intBase);  // Update Base Motor State
					         DB.close();
					 	 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					 }
					 Get_Current_State(1);
				 }
				 else if (intBase < 0)  // IF ROBOT IS IN  THE RIGHT SIDE (MOVE TO LEFT FOR RESET)
				 {
					 for (int i = intBase; i < 0; i++)
					 {
						 try
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 'a', false);
						     intBase++;
						     Thread.sleep(1000);
						     Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
						     DB.open();
						     DB.updateBaseM_State(1, intBase);  // Update Base Motor State
						     DB.close();
						     AllReset = true;
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					 }
					 Get_Current_State(1);
				 }
				 
				 // VERIFY SHOULDER STATE
				 if (intShoulder == 0)
				 {
					 AllReset = true;
				 }
				 else
				 {
					 for (int i = intShoulder; i > 0; i--)
					 {
						 try
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 'c', false);
							 intShoulder--;
						     Thread.sleep(1000);
					         Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					         DB.open();
					         DB.updateShoulderM_State(1,intShoulder);  // Update Shoulder Motor State
					         DB.close();
					         AllReset = true;
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					 }
					 Get_Current_State(2);
				 }
				
				 //VERIFY ELBOW STATE
			     if (intElbow == 0)
			     {
			    	 AllReset = true;
			     }
			     else
			     {
			    	 for (int i = intElbow; i > 0; i--)
					 {
						 try
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 'f', false);
							 intElbow--;
						     Thread.sleep(1000);
					         Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					         DB.open();
					         DB.updateElbowM_State(1, intElbow);  // Update Elbow Motor State
					         DB.close();
					         AllReset = true;
					     }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					 }
			    	 Get_Current_State(3);
		         }
			   
			     // VERIFY WRIST STATE 
			     if (intWrist == 0)
			     {
			    	 AllReset = true;
			     }
			     else
			     {
			    	 for (int i = intWrist; i > 0; i--)
					 {
						 try
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 'h', false);
							 intWrist--;
						     Thread.sleep(1000);
					         Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					         DB.open();
					         DB.updateWristM_State(1, intWrist);  // UPDATE WRIST MOTOR STATE
					         DB.close();
					         AllReset = true;
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					 }
			    	 Get_Current_State(4);
			     }
			     
			     // VERIFY HAND STATE
			     if (intHand == 0)
			     {
			    	 AllReset = true;
				 }
			     else  // RESET HAND STATE (CLOSE HAND)
			     {
			    	 for (int i = intHand; i > 0; i--)
					 {
						 try
						 {
							  Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 'j', false);
					          intHand--;
					          Thread.sleep(600);
					          Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					          DB.open();
					          DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE
					          DB.close();
					          AllReset = true;
						 }
						 
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(ResetState.this, DEVICE_ADDRESS, 's', false);
					 }
			    	 Get_Current_State(5);
			     }
			    
			     if(AllReset == true)
			     {
			    	 Toast.makeText(getBaseContext(), "Robot Arm is in RESET State",Toast.LENGTH_SHORT).show(); 
			     }
			     // remove call back
			     mHandler.removeCallbacks(Reset_Robot_State); 
	        	
	        } // end run() method
	    }; 
	    
	    /** Method: Get_Current_State()
	     *  Update the Screen with the new robot state
	     *  @param void
	     *  @return void
	     */
	    private void Get_Current_State(int opt)
	    {
	    	Cursor A;
	    	DB.open(); 
	     	
	     	switch(opt)
	     	{
	     	    case 1:
	     	        A = DB.getBaseM_State(1);
			        A.moveToFirst();
			        txtBaseMotor.setText(A.getString(0));
			        break;
	     	    case 2:
	     	        A = DB.getShoulderM_State(1);
			        A.moveToFirst();
			        txtShoulderMotor.setText(A.getString(0));
			        break;
	     	    case 3:
	    	        A = DB.getElbowM_State(1);
			        A.moveToFirst();
			        txtElbowMotor.setText(A.getString(0));
			        break;
	     	    case 4:
	     	       A = DB.getWristM_State(1);
			       A.moveToFirst();
			       txtWristMotor.setText(A.getString(0));
			       break;
	     	    case 5:
	      	       A = DB.getHandM_State(1);
			       A.moveToFirst();
			       txtHandMotor.setText(A.getString(0));
			       break;
	     	} // end switch	    	
			DB.close();
        } // end method
	    
	    @Override 
	    protected void onPause() { 
	    	mHandler.removeCallbacks(Reset_Robot_State); 
	        super.onPause(); 
	    } 
	 
	    @Override 
	    protected void onResume() { 
	        super.onResume(); 
	        mHandler.removeCallbacks(Reset_Robot_State); 
	    } 
	 
}   // END CLASS

//*****************************************************************************
//****************************************************************************
//C H A N G E   L O G
//*****************************************************************************
//04/18/2012    ABBAS ALSHAFAI/EDGAR ACOSTA   Initial Release
