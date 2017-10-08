//**************************************************************
// CLASS:  Calibration
// PURPOSE:  Implements a manual control for the Roobot arm and 
//          for reposition, and a Reset method, for reset all Motor state
// FINAL PROJECT:  MOBILE APPLICATIONS 
// STUDENTS:   EDGAR ACOSTA / ABBAS ALSHAFAI
// MASTER OF SCIENCE IN ENGINEERING SOFTWARE ENGINEERING
// BS COMPUTER SCIENCE
// THE UNIVERSITY OF TEXAS AT EL PASO
//***************************************************************

package edg.ROBOT.mob;

//Libraries
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import at.abraxas.amarino.Amarino; /* AMARINO LIBRARY BLUETOOTH */

/** Class: Calibration
 *  Calibrate Robot Arm (Repositioning and Reseting Data Base to Zero)
 *  @author Edgar Acosta / Abbas Alshafai
 */
public class Calibration extends Activity
{
    private static final String TAG = "Calibration";
	
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
	Button cmdExecuteCalibration;
	Button cmdReturn;
	
	TextView baseCal;
	TextView shoulderCal;
	TextView elbowCal;
	TextView handCal;
	TextView wristCal;
	TextView calRezeroRobot;
	TextView backCal;
	
	Typeface font;
	
	private static final String DEVICE_ADDRESS = "00:06:66:08:5E:F1";
	
	DBInterface DB = new DBInterface(this);  //Data Base Object
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calibration);
     
        cmdReturn = (Button)findViewById(R.id.cmdCalibrationReturn);
        cmdExecuteCalibration = (Button)findViewById(R.id.cmdExecuteCalibration);
        cmdBaseLeft = (Button)findViewById(R.id.cmdCalibrationBaseLeft);
        cmdBaseRight = (Button)findViewById(R.id.cmdCalibrationBaseRight);
        cmdShoulderUp = (Button)findViewById(R.id.cmdCalibrationShoulderUp);
    	cmdShoulderDown = (Button)findViewById(R.id.cmdCalibrationShoulderDown);
    	cmdElbowUp = (Button)findViewById(R.id.cmdCalibrationElbowUp);
    	cmdElbowDown = (Button)findViewById(R.id.cmdCalibrationElbowDown);
    	cmdWristUp = (Button)findViewById(R.id.cmdCalibrationWristUp);
    	cmdWristDown = (Button)findViewById(R.id.cmdCalibrationWristDown);
    	cmdHandOpen = (Button)findViewById(R.id.cmdCalibrationHandOpen);
    	cmdHandClose = (Button)findViewById(R.id.cmdCalibrationHandClose);
    	
    	font = Typeface.createFromAsset(getAssets(), "moderno.ttf");
    	
    	baseCal = (TextView) findViewById(R.id.baseCal);
    	baseCal.setTypeface(font);

    	wristCal = (TextView) findViewById(R.id.wristCal);
    	wristCal.setTypeface(font);
    	
    	shoulderCal = (TextView) findViewById(R.id.shoulderCal);
    	shoulderCal.setTypeface(font);
    	
    	elbowCal = (TextView) findViewById(R.id.elbowCal);
    	elbowCal.setTypeface(font);
    	
    	handCal = (TextView) findViewById(R.id.handCal);
    	handCal.setTypeface(font);
    	
    	calRezeroRobot = (TextView) findViewById(R.id.CalRezeroRobot);
    	calRezeroRobot.setTypeface(font);
    	
    	backCal = (TextView) findViewById(R.id.returnCal);
    	backCal.setTypeface(font);
    	
    	// CONNECTING USING BLUETOOTH TO ARDUINO
        Amarino.connect(this, DEVICE_ADDRESS);
        
        /**Method:  cmdBaseLeft.setOnClickListener.
         * Send Signal to Base left Arm Motor
         */
        cmdBaseLeft.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
			    // SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'a', false);
				     Thread.sleep(1000);
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				
			}
		});
        /**Method: cmdBaseRight.setOnClickListener
         * Send Signal to Base Right Arm motor
         */
        cmdBaseRight.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'b', false);
				    Thread.sleep(1000);
				    Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
			}
		});
        
        /**Method: cmdShoulderUp.setOnClickListener.
         * Send Signal to Shoulder Up Arm motor
         */        
        cmdShoulderUp.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'd', false);
				     Thread.sleep(1000);
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
	 			}
				catch(InterruptedException e)
				{
					 Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
			}
		});
        
        /**Method: cmdShoulderDown.setOnClickListener.
         * Send Signal to Shoulder Down Arm motor
         */
        cmdShoulderDown.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
			   // SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'c', false);
				     Thread.sleep(1000);
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
	   		    }
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);				
			}
		});
        
        /**Method: cmdElbowUp.setOnClickListener
         * Send Signal to Elbow Up Arm motor
         */
        cmdElbowUp.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					  Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'e', false);
					  Thread.sleep(1000);
				      Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
			}
		});
        
        /**Method: cmdElbowDown.setOnClickListener
         * Send Signal to Elbow Down Arm motor
         */
        cmdElbowDown.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
			   // SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
		  	 	     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'f', false);
					 Thread.sleep(1000);
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				
			}
		});
        
        /**Method: cmdWristUp.setOnClickListener
         * Send Signal to Wrist Up Arm motor
         */
        cmdWristUp.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'g', false);
				    Thread.sleep(1000);
			        Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
			}
		});
		
		/**Method: cmdWristDown.setOnClickListener
         * Send Signal to Wrist Down Arm motor
         */
        cmdWristDown.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
				    Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'h', false);
			        Thread.sleep(1000);
			        Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
			}
		});
		
		/**Method: cmdHandOpen.setOnClickListener.
         * Send Signal to Hand Open Arm motor
         */
    
		cmdHandOpen.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
					 Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'i', false);
				     Thread.sleep(600);
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
			}
		});
		
		/**Method: cmdHandClose.setOnClickListener.
         * Send Signal to Hand Close Arm motor
         */
    	
		cmdHandClose.setOnClickListener(new View.OnClickListener()
		 {
			@Override
			public void onClick(View v)
		    {
		    // SEND DATA TO ARDUINO USING BLUETOOTH
				try
				{
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 'j', false);
				     Thread.sleep(600);
				     Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				catch(InterruptedException e)
				{
					Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);
				}
				Amarino.sendDataToArduino(Calibration.this, DEVICE_ADDRESS, 's', false);	
			}
		});
        
        /** Method: cmdExecuteCalibration.setOnClickListener
         *  Execute a Reset in data base setting all Robot State to Zero
         */
        cmdExecuteCalibration.setOnClickListener(new View.OnClickListener()
        {
			
			@Override
			public void onClick(View v)
			{
			    DB.open();     // Data Base Open
			    DB.ReZero(1);  // Reset Data Base
			    Toast.makeText(getBaseContext(),"Data Base ReZeroed..." , Toast.LENGTH_SHORT).show();
			    DB.close();    // Data Base Close
			}
		});
        
        /** Method:cmdReturn.setOnClickListener
         *   Return to Main Menu Activity
         */
        cmdReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				 DB.close();
			     finish();				
			}
		});
    	
    	
    } // end onCreate	
    	
}//End Class

//*****************************************************************************
//****************************************************************************
//C H A N G E   L O G
//*****************************************************************************
//04/15/2012    ABBAS ALSHAFAI/EDGAR ACOSTA   Initial Release
