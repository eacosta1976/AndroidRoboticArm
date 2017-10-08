//************************************************************************
// CLASS:  AutomatedSequence
// PURPOSE:  Implements an Automated Sequence for the Robot Arm which consist
//           to start from a Reset State, then move in front, and then move left, 
//           go down, open a hand, close hand, go left and release, terminates in the
//           center.
// FINAL PROJECT:  MOBILE APPLICATIONS 
// STUDENTS:   EDGAR ACOSTA / ABBAS
// MASTER OF SCIENCE IN ENGINEERING SOFTWARE ENGINEERING
// BS COMPUTER SCIENCE
// THE UNIVERSITY OF TEXAS AT EL PASO
//************************************************************************


package edg.ROBOT.mob;

// LIBARIES

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.database.Cursor;
import android.graphics.Typeface;
import android.widget.Toast;
import at.abraxas.amarino.Amarino; /* AMARINO LIBRARY BLUETOOTH */

/** Class:  AutomatedSecuence.
 * Implements an Automated Secuence of Movements (Robot From Left to Right)
 * @author Edgar Acosta, Abbas Alshafai
 */
public class AutomatedSecuence extends Activity
{
	DBInterface DB = new DBInterface(this);  //Data Base Object
    private static final String TAG = "AutomatedSecuence";
    private static final String DEVICE_ADDRESS = "00:06:66:08:5E:F1";
    
	 // widgets
	 Button cmdExecuteSecuence;
     Button cmdReturn;
	 TextView txtBaseMotor;
	 TextView txtShoulderMotor;
	 TextView txtElbowMotor;
	 TextView txtWristMotor;
	 TextView txtHandMotor;
	 TextView returnTxt;
	 TextView execTxt;
	 Typeface font;
	 
	 
	 int intBase = 0;
	 int intShoulder = 0;
	 int intElbow = 0;
	 int intWrist = 0;
	 int intHand = 0;
	 
	 boolean AllReset = false;
	
	 /**Method:  onCreate()
	  *
	  */
	 public void onCreate(Bundle savedInstanceState)
	 {
	     super.onCreate(savedInstanceState);
	     setContentView(R.layout.automatedsecuence);
	     
	     cmdExecuteSecuence = (Button)findViewById(R.id.cmdExecuteSecuence);
	     cmdReturn = (Button)findViewById(R.id.cmdReturnSecuence);
	     txtBaseMotor = (TextView)findViewById(R.id.txtSecuenceBaseMotor);
		 txtShoulderMotor = (TextView)findViewById(R.id.txtSecuenceShoulderMotor);
		 txtElbowMotor = (TextView)findViewById(R.id.txtSecuenceElbowMotor);
		 txtWristMotor = (TextView)findViewById(R.id.txtSecuenceWristMotor);
		 txtHandMotor = (TextView)findViewById(R.id.txtSecuenceHandMotor);
		 
		 font = Typeface.createFromAsset(getAssets(), "moderno.ttf");
		 
		 returnTxt = (TextView) findViewById(R.id.returnTxt);
		 returnTxt.setTypeface(font);
		 
		 execTxt = (TextView) findViewById(R.id.execTxt);
		 execTxt.setTypeface(font);
		 
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
		 
		 DB.close();    // Close Data Base
		 
		 
		 /**  cmdExecuteSecuence.setOnClickListener
		  *   Execute Automated Secuence
		  */
		 execTxt.setOnClickListener(new View.OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{
				 DB.open();   // Open Data Base
				 // verify position of each motor taking reference the Reset state
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'b', false);
					         intBase--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateBaseM_State(1, intBase);  // Update Base Motor State
					         // Update Screen
					         Cursor A = DB.getCurrent_State();
							 A.moveToFirst();
							 txtBaseMotor.setText(A.getString(1)); 
							 txtShoulderMotor.setText(A.getString(2));
							 txtElbowMotor.setText(A.getString(3));
							 txtWristMotor.setText(A.getString(4));
							 txtHandMotor.setText(A.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } //end for
					 AllReset = true;
				 }
				 else if (intBase < 0)  // IF ROBOT IS IN  THE RIGHT SIDE (MOVE TO LEFT FOR RESET)
				 {
					 for (int i = intBase; i < 0; i++)
					 {
						 try
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'a', false);
						     intBase++;
						     Thread.sleep(1000);
						     Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						     DB.updateBaseM_State(1, intBase);  // Update Base Motor State
						     // Update Screen
					         Cursor C = DB.getCurrent_State();
							 C.moveToFirst();
							 txtBaseMotor.setText(C.getString(1)); 
							 txtShoulderMotor.setText(C.getString(2));
							 txtElbowMotor.setText(C.getString(3));
							 txtWristMotor.setText(C.getString(4));
							 txtHandMotor.setText(C.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } // end for
					 AllReset = true;
					 
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'c', false);
							 intShoulder--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateShoulderM_State(1,intShoulder);  // Update Shoulder Motor State
					         // Update Screen
					         Cursor E = DB.getCurrent_State();
							 E.moveToFirst();
							 txtBaseMotor.setText(E.getString(1)); 
							 txtShoulderMotor.setText(E.getString(2));
							 txtElbowMotor.setText(E.getString(3));
							 txtWristMotor.setText(E.getString(4));
							 txtHandMotor.setText(E.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 }//end for
					 AllReset = true;
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'f', false);
							 intElbow--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateElbowM_State(1, intElbow);  // Update Elbow Motor State
					         // Update Screen
					         Cursor F = DB.getCurrent_State();
							 F.moveToFirst();
							 txtBaseMotor.setText(F.getString(1)); 
							 txtShoulderMotor.setText(F.getString(2));
							 txtElbowMotor.setText(F.getString(3));
							 txtWristMotor.setText(F.getString(4));
							 txtHandMotor.setText(F.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } // end for
			    	 AllReset = true;
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'h', false);
							 intWrist--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateWristM_State(1, intWrist);  // UPDATE WRIST MOTOR STATE
					         // Update Screen
					         Cursor G = DB.getCurrent_State();
							 G.moveToFirst();
							 txtBaseMotor.setText(G.getString(1)); 
							 txtShoulderMotor.setText(G.getString(2));
							 txtElbowMotor.setText(G.getString(3));
							 txtWristMotor.setText(G.getString(4));
							 txtHandMotor.setText(G.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } //end for
			    	 AllReset = true;
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
							  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'j', false);
					          intHand--;
					          Thread.sleep(600);
					          Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					          DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE
					          // Update Screen
						      Cursor H = DB.getCurrent_State();
							  H.moveToFirst();
						      txtBaseMotor.setText(H.getString(1)); 
							  txtShoulderMotor.setText(H.getString(2));
							  txtElbowMotor.setText(H.getString(3));
							  txtWristMotor.setText(H.getString(4));
							  txtHandMotor.setText(H.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 }//end for
			    	 AllReset = true;
			     }
			    
			     
			     // RESET STATE THEN START AUTOMATED SEQUENCE
			     if(AllReset == true)
			     {
			    	  
			    	 try
			    	 {
			    		   //ELBOW UP 4
			    		   for(int i = 0; i < 4; i++)
			    		   {
			    		        Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'e', false);
			    		        intElbow++;
			    	            Thread.sleep(1000);
				                Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
				                DB.updateElbowM_State(1, intElbow);  // Update Elbow Motor State
			    		   }
				           
				           
				           //WRIST UP 2
			    		   for(int i = 0; i <= 2; i++)
			    		   {
				               Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'g', false);
				               intWrist++;
				               Thread.sleep(1000);
					           Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					           DB.updateWristM_State(1, intWrist);   //UPDATE WRIST MOTOR STATE
			    		   }
			    		   
			    		   //SHOULDER UP 3
			    		   for(int i = 0; i <= 3; i++)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'd', false);
			    			   intShoulder++;
			    		       Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateShoulderM_State(1,intShoulder);  // Update Shoulder Motor State
			    		   }
			    		   
			    		   //BASE LEFT 2
			    		   for(int i = 0; i <= 2; i++)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'a', false);
			    			   intBase++;
			    		       Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateBaseM_State(1, intBase);  // Update Base Motor State
			    		   }
				           
			    		   //WRISTDOWN
			    		   for(int i = 2; i >= 0; i--)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'h', false);
			    			   intWrist--;
			  		           Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateWristM_State(1, intWrist);  // UPDATE WRIST MOTOR STATE
			    		   }
			    		   //HAND OPEN
			    		   for(int i = 0; i <= 2; i++)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'i', false);
			    			   intHand++;
		  			           Thread.sleep(600);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE   
			    		   }
			    		   //HAND CLOSE
			    		   for(int i = 2; i >= 0; i--)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'j', false);
			    			   intHand--;
			 			       Thread.sleep(600);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE
			    		   }
			    		   
			    		 //WRIST UP 2
			    		 for(int i = 0; i <= 2; i++)
			    		 {
				               Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'g', false);
				               intWrist++;
					           Thread.sleep(1000);
					           Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					           DB.updateWristM_State(1, intWrist);   //UPDATE WRIST MOTOR STATE
			    		 }
			    		 // BASE RIGHT
			    		 for(int i = 0; i <= 5; i++)
			    		 {
			    			 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'b', false);
			    			 intBase--;
		   		             Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateBaseM_State(1, intBase);  // Update Base Motor State
			    		 } 
			    		 
			    		 //WRISTDOWN
			    		 for(int i = 2; i >= 0; i--)
			    		 {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'h', false);
			    			   intWrist--;
			     		       Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateWristM_State(1, intWrist);  // UPDATE WRIST MOTOR STATE
			    		 }
			    		 //HAND OPEN
			    		 for(int i = 0; i <= 2; i++)
			    		 {
			    			  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'i', false);
			    			  intHand++;
			      	          Thread.sleep(600);
						      Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						      DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE   
			    		 }
			    		 //HAND CLOSE
			    		 for(int i = 2; i >= 0; i--)
			    		 {
			    			  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'j', false);
			    			  intHand--;
			    			  Thread.sleep(600);
						      Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						      DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE
			    		 }
			    		   
			    		 //WRIST UP 2
			    		 for(int i = 0; i <= 2; i++)
			    		 {
				              Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'g', false);
				              intWrist++;
				              Thread.sleep(1000);
					          Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					          DB.updateWristM_State(1, intWrist);   //UPDATE WRIST MOTOR STATE
			    		 }
			    		 
			    		//BASE LEFT 2
			    		for(int i = 0; i <= 2; i++)
			    		{
			    			  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'a', false);
			    			  intBase++;
			    			  Thread.sleep(1000);
						      Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						      DB.updateBaseM_State(1, intBase);  // Update Base Motor State
			    		}
			    		
			    		Cursor Z = DB.getCurrent_State();
						Z.moveToFirst();
					    txtBaseMotor.setText(Z.getString(1)); 
						txtShoulderMotor.setText(Z.getString(2));
						txtElbowMotor.setText(Z.getString(3));
						txtWristMotor.setText(Z.getString(4));
						txtHandMotor.setText(Z.getString(5));
			    		
						DB.close(); // CLOSE DATA BASE
			    		
			    		 
			    	 } // End Try
			    	 catch(InterruptedException e)
			    	 {
			    		 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 DB.close();
			    	 }
			    	 
			    }  // end if
			   				
			}
		});  // end onClickListener
		 
		 
		 /**  cmdExecuteSecuence.setOnClickListener
		  *   Execute Automated Secuence
		  */
		 cmdExecuteSecuence.setOnClickListener(new View.OnClickListener()
		 {
			@Override
			public void onClick(View v) 
			{
				 DB.open();   // Open Data Base
				 // verify position of each motor taking reference the Reset state
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'b', false);
					         intBase--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateBaseM_State(1, intBase);  // Update Base Motor State
					         // Update Screen
					         Cursor A = DB.getCurrent_State();
							 A.moveToFirst();
							 txtBaseMotor.setText(A.getString(1)); 
							 txtShoulderMotor.setText(A.getString(2));
							 txtElbowMotor.setText(A.getString(3));
							 txtWristMotor.setText(A.getString(4));
							 txtHandMotor.setText(A.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } //end for
					 AllReset = true;
				 }
				 else if (intBase < 0)  // IF ROBOT IS IN  THE RIGHT SIDE (MOVE TO LEFT FOR RESET)
				 {
					 for (int i = intBase; i < 0; i++)
					 {
						 try
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'a', false);
						     intBase++;
						     Thread.sleep(1000);
						     Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						     DB.updateBaseM_State(1, intBase);  // Update Base Motor State
						     // Update Screen
					         Cursor C = DB.getCurrent_State();
							 C.moveToFirst();
							 txtBaseMotor.setText(C.getString(1)); 
							 txtShoulderMotor.setText(C.getString(2));
							 txtElbowMotor.setText(C.getString(3));
							 txtWristMotor.setText(C.getString(4));
							 txtHandMotor.setText(C.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } // end for
					 AllReset = true;
					 
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'c', false);
							 intShoulder--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateShoulderM_State(1,intShoulder);  // Update Shoulder Motor State
					         // Update Screen
					         Cursor E = DB.getCurrent_State();
							 E.moveToFirst();
							 txtBaseMotor.setText(E.getString(1)); 
							 txtShoulderMotor.setText(E.getString(2));
							 txtElbowMotor.setText(E.getString(3));
							 txtWristMotor.setText(E.getString(4));
							 txtHandMotor.setText(E.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 }//end for
					 AllReset = true;
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'f', false);
							 intElbow--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateElbowM_State(1, intElbow);  // Update Elbow Motor State
					         // Update Screen
					         Cursor F = DB.getCurrent_State();
							 F.moveToFirst();
							 txtBaseMotor.setText(F.getString(1)); 
							 txtShoulderMotor.setText(F.getString(2));
							 txtElbowMotor.setText(F.getString(3));
							 txtWristMotor.setText(F.getString(4));
							 txtHandMotor.setText(F.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } // end for
			    	 AllReset = true;
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
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'h', false);
							 intWrist--;
					         Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateWristM_State(1, intWrist);  // UPDATE WRIST MOTOR STATE
					         // Update Screen
					         Cursor G = DB.getCurrent_State();
							 G.moveToFirst();
							 txtBaseMotor.setText(G.getString(1)); 
							 txtShoulderMotor.setText(G.getString(2));
							 txtElbowMotor.setText(G.getString(3));
							 txtWristMotor.setText(G.getString(4));
							 txtHandMotor.setText(G.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 } //end for
			    	 AllReset = true;
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
							  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'j', false);
					          intHand--;
					          Thread.sleep(600);
					          Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					          DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE
					          // Update Screen
						      Cursor H = DB.getCurrent_State();
							  H.moveToFirst();
						      txtBaseMotor.setText(H.getString(1)); 
							  txtShoulderMotor.setText(H.getString(2));
							  txtElbowMotor.setText(H.getString(3));
							  txtWristMotor.setText(H.getString(4));
							  txtHandMotor.setText(H.getString(5));
						 }
						 catch(InterruptedException e)
						 {
							 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 }
						 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					 }//end for
			    	 AllReset = true;
			     }
			    
			     
			     // RESET STATE THEN START AUTOMATED SEQUENCE
			     if(AllReset == true)
			     {
			    	  
			    	 try
			    	 {
			    		   //ELBOW UP 4
			    		   for(int i = 0; i < 4; i++)
			    		   {
			    		        Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'e', false);
			    		        intElbow++;
			    	            Thread.sleep(1000);
				                Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
				                DB.updateElbowM_State(1, intElbow);  // Update Elbow Motor State
			    		   }
				           
				           
				           //WRIST UP 2
			    		   for(int i = 0; i <= 2; i++)
			    		   {
				               Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'g', false);
				               intWrist++;
				               Thread.sleep(1000);
					           Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					           DB.updateWristM_State(1, intWrist);   //UPDATE WRIST MOTOR STATE
			    		   }
			    		   
			    		   //SHOULDER UP 3
			    		   for(int i = 0; i <= 3; i++)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'd', false);
			    			   intShoulder++;
			    		       Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateShoulderM_State(1,intShoulder);  // Update Shoulder Motor State
			    		   }
			    		   
			    		   //BASE LEFT 2
			    		   for(int i = 0; i <= 2; i++)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'a', false);
			    			   intBase++;
			    		       Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateBaseM_State(1, intBase);  // Update Base Motor State
			    		   }
				           
			    		   //WRISTDOWN
			    		   for(int i = 2; i >= 0; i--)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'h', false);
			    			   intWrist--;
			  		           Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateWristM_State(1, intWrist);  // UPDATE WRIST MOTOR STATE
			    		   }
			    		   //HAND OPEN
			    		   for(int i = 0; i <= 2; i++)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'i', false);
			    			   intHand++;
		  			           Thread.sleep(600);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE   
			    		   }
			    		   //HAND CLOSE
			    		   for(int i = 2; i >= 0; i--)
			    		   {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'j', false);
			    			   intHand--;
			 			       Thread.sleep(600);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE
			    		   }
			    		   
			    		 //WRIST UP 2
			    		 for(int i = 0; i <= 2; i++)
			    		 {
				               Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'g', false);
				               intWrist++;
					           Thread.sleep(1000);
					           Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					           DB.updateWristM_State(1, intWrist);   //UPDATE WRIST MOTOR STATE
			    		 }
			    		 // BASE RIGHT
			    		 for(int i = 0; i <= 5; i++)
			    		 {
			    			 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'b', false);
			    			 intBase--;
		   		             Thread.sleep(1000);
					         Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					         DB.updateBaseM_State(1, intBase);  // Update Base Motor State
			    		 } 
			    		 
			    		 //WRISTDOWN
			    		 for(int i = 2; i >= 0; i--)
			    		 {
			    			   Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'h', false);
			    			   intWrist--;
			     		       Thread.sleep(1000);
						       Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						       DB.updateWristM_State(1, intWrist);  // UPDATE WRIST MOTOR STATE
			    		 }
			    		 //HAND OPEN
			    		 for(int i = 0; i <= 2; i++)
			    		 {
			    			  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'i', false);
			    			  intHand++;
			      	          Thread.sleep(600);
						      Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						      DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE   
			    		 }
			    		 //HAND CLOSE
			    		 for(int i = 2; i >= 0; i--)
			    		 {
			    			  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'j', false);
			    			  intHand--;
			    			  Thread.sleep(600);
						      Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						      DB.updateHandM_State(1, intHand);  // UPDATE HAND MOTOR STATE
			    		 }
			    		   
			    		 //WRIST UP 2
			    		 for(int i = 0; i <= 2; i++)
			    		 {
				              Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'g', false);
				              intWrist++;
				              Thread.sleep(1000);
					          Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
					          DB.updateWristM_State(1, intWrist);   //UPDATE WRIST MOTOR STATE
			    		 }
			    		 
			    		//BASE LEFT 2
			    		for(int i = 0; i <= 2; i++)
			    		{
			    			  Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 'a', false);
			    			  intBase++;
			    			  Thread.sleep(1000);
						      Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						      DB.updateBaseM_State(1, intBase);  // Update Base Motor State
			    		}
			    		
			    		Cursor Z = DB.getCurrent_State();
						Z.moveToFirst();
					    txtBaseMotor.setText(Z.getString(1)); 
						txtShoulderMotor.setText(Z.getString(2));
						txtElbowMotor.setText(Z.getString(3));
						txtWristMotor.setText(Z.getString(4));
						txtHandMotor.setText(Z.getString(5));
			    		
						DB.close(); // CLOSE DATA BASE
			    		
			    		 
			    	 } // End Try
			    	 catch(InterruptedException e)
			    	 {
			    		 Amarino.sendDataToArduino(AutomatedSecuence.this, DEVICE_ADDRESS, 's', false);
						 DB.close();
			    	 }
			    	 
			    }  // end if
			   				
			}
		});  // end onClickListener
		 
		 
		 /**Method: returnTxt.setOnClickListener
		  * Return to Main Activity
		  */
		 cmdReturn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
			    DB.close();
				finish();				
			}
		});
		 
		 /**Method: cmdReturn.setOnClickListener
		  * Return to Main Activity
		  */
		 returnTxt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
			    DB.close();
				finish();				
			}
		}); 
		 
	 }// end onCreate()

}  // End Class

//*****************************************************************************
//****************************************************************************
//C H A N G E   L O G
//*****************************************************************************
//04/19/2012    ABBAS ALSHAFAI/EDGAR ACOSTA   Initial Release



