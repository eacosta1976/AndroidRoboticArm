//**************************************************************
// CLASS:  ANDROID_ROBOT_ARMActivity
// PURPOSE:  MAIN CLASS OF THE ANDROID_ROBOT_ARM APP
//           IMPLEMENTING THE MAIN MENU
// FINAL PROJECT:  MOBILE APPLICATIONS 
// STUDENTS:   EDGAR ACOSTA / ABBAS ALSHAFAI
// MASTER OF SCIENCE IN ENGINEERING SOFTWARE ENGINEERING
// BS COMPUTER SCIENCE
// THE UNIVERSITY OF TEXAS AT EL PASO
//***************************************************************

package edg.ROBOT.mob;

/* LIBRARIES */
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;  

/** Class ANDROID_ROBOT_ARMActivity.
 *  Main Activity implementing main menu of ANDROID_ROBOT_ARM App
 * @author Edgar Acosta
 */
public class ANDROID_ROBOT_ARMActivity extends Activity {
    
	DBInterface DB = new DBInterface(this);  //Data Base Object
	
	// WIDGETS
	Button cmdManualControl;
	Button cmdResetState;
	Button cmdAutomatedSecuence;
	Button cmdBrowseStateMain;
	Button cmdCalibrateRobotArm;
	
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        cmdManualControl = (Button)findViewById(R.id.cmdManualControl);
        cmdResetState = (Button)findViewById(R.id.cmdResetState);
        cmdAutomatedSecuence = (Button)findViewById(R.id.cmdAutomatedSecuenceActivity);
        cmdBrowseStateMain = (Button)findViewById(R.id.cmdBrowseStateMain);
        cmdCalibrateRobotArm = (Button)findViewById(R.id.cmdCalibrationActivity);
        
        /** Method: cmdManualControl.setOnClickListener
         *  Calls ManualControl Activity
         */
        cmdManualControl.setOnClickListener(new View.OnClickListener()
        {
        	public void onClick(View v)
			{
				startActivity(new Intent("edg.ROBOT.MANUALCONTROL"));
			}
		});
        
        /** Method: cmdResetState.setOnClickListener
         *  Send to ResetState Activity to Reset Robot State
         */
        cmdResetState.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				startActivity(new Intent("edg.ROBOT.RESETSTATE"));
			}
		});
        
        /** Method: cmdAutomatedSecuence.setOnClickListener.
         *  Send to AutomatedSecuence
         */
        cmdAutomatedSecuence.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent("edg.ROBOT.AUTOMATEDSECUENCE"));				
			}
		});
        
        /** Method: cmdBrowseStateMain.setOnClickListener
         *  Send to BrowseState Activity to Browse Robot State
         */
        cmdBrowseStateMain.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent("edg.ROBOT.CURRENTSTATE"));
			}
		});
        
        /** Method: cmdCalibrateRobotArm.setOnClickListener
         *  Position the Robot Arm in Initial State and Reset Data Base
         */
        cmdCalibrateRobotArm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent("edg.ROBOT.CALIBRATION"));	
			}
		});
 
    } // End onCreate
} // End Class

//*****************************************************************************
//****************************************************************************
//  C H A N G E   L O G
//*****************************************************************************
// 04/15/2012    ABBAS ALSHAFAI/EDGAR ACOSTA   Initial Release