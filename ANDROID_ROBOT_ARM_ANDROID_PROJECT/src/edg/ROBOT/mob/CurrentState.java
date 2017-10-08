//************************************************************************
// CLASS:  CurrentState
// PURPOSE:  Implements a Monitor to Verify the current state of the Robot Arm    
// FINAL PROJECT:  MOBILE APPLICATIONS 
// STUDENTS:   EDGAR ACOSTA / ABBAS
// MASTER OF SCIENCE IN ENGINEERING SOFTWARE ENGINEERING
// BS COMPUTER SCIENCE
// THE UNIVERSITY OF TEXAS AT EL PASO
//************************************************************************

package edg.ROBOT.mob;

// Libraries
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.database.Cursor;
import android.graphics.Typeface;
import android.widget.Toast;

/** Class CurrentState.
 *  Browse the current state of the robot
 * @author edgar
 */
public class CurrentState extends Activity
{
	 DBInterface DB = new DBInterface(this);  //Data Base Object
	 
	 // widgets
	 Button cmdReturn;
	 EditText txtBaseMotor;
	 EditText txtShoulderMotor;
	 EditText txtElbowMotor;
	 EditText txtWristMotor;
	 EditText txtHandMotor;
	 TextView returnTxt;
	 Typeface font;
	 
	 /**Method onCreate()
	  */
	 public void onCreate(Bundle savedInstanceState)
	 {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.currentstate);
	     
	     cmdReturn = (Button)findViewById(R.id.cmdBrowseControlReturn);
	     txtBaseMotor = (EditText)findViewById(R.id.txtBaseMotor);
		 txtShoulderMotor = (EditText)findViewById(R.id.txtShoulderMotor);
		 txtElbowMotor = (EditText)findViewById(R.id.txtElbowMotor);
		 txtWristMotor = (EditText)findViewById(R.id.txtWristMotor);
		 txtHandMotor = (EditText)findViewById(R.id.txtHandMotor);

		 font = Typeface.createFromAsset(getAssets(), "moderno.ttf");		 
		 returnTxt = (TextView) findViewById(R.id.returnTxt);
		 returnTxt.setTypeface(font);
		 
		 
		// GET CURRENT STATE FROM DATA BASE 
		 DB.open();
		 Cursor C = DB.getCurrent_State();
		 C.moveToFirst();
		 txtBaseMotor.setText(C.getString(1)); 
		 txtShoulderMotor.setText(C.getString(2));
		 txtElbowMotor.setText(C.getString(3));
		 txtWristMotor.setText(C.getString(4));
		 txtHandMotor.setText(C.getString(5));
		 DB.close(); 
	
		 // Return to Main Menu Activity
		 cmdReturn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
			    finish();				
			}
		});
	     
		 
		 // Return to Main Menu Activity
		 returnTxt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
			    finish();				
			}
		});
	 }  // End OnCreate()  

}  // End Class

//*****************************************************************************
//****************************************************************************
//C H A N G E   L O G
//*****************************************************************************
//04/18/2012    ABBAS ALSHAFAI/EDGAR ACOSTA   Initial Release
