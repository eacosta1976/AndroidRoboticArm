//***********************************************************************
// PROJECT:   ANDROID-ARDUINO  ROBOT ARM
// FINAL PROJECT FOR MOBILE APPLICATION COURSE
// DATE: 04/13/2012
// COURSE:   SPECIAL TOPICS IN COMPUTER SCIENCE - MOBILE APPLICATIONS
// STUDENTS:  EDGAR ACOSTA DAVILA,   ABBAS ALSHAFAI
// PROFESSOR:  DR. YONSIK CHEON
//  THE UNIVERSITY OF TEXAS AT EL PASO
//************************************************************************

// LIBRARIES
#include <MeetAndroid.h>              // Amarino Bluetooth

//DEFINITIONS
#define baudRate 9600

//***********************************************************************
//  PINs VARIABLE DECLARATIONS

//  SHOULDER MOTOR PIN DEFINITION
int shoulderMotorEnablePin = 2;
int shoulderMotorPin1 = 3;
int shoulderMotorPin2 = 4;

// BASE MOTOR PIN DEFINITION
int baseMotorEnablePin = 14;
int baseMotorPin1 = 15;
int baseMotorPin2 = 16;

// WRIST MOTOR PIN DEFINITION
int wristMotorEnablePin = 8;
int wristMotorPin1 = 9;
int wristMotorPin2 = 10;

// ELBOW MOTOR PIN DEFINITION
int elbowMotorEnablePin = 5;
int elbowMotorPin1 = 6;
int elbowMotorPin2 = 7;

// HAND MOTOR PIN DEFINITION
int handMotorEnablePin = 11;
int handMotorPin1 = 17;
int handMotorPin2 = 18; 

// BLUETOOTH AMARINO OBJECT
 MeetAndroid phone;

// COUNTER
int counter = 0;

//********************************************************
// METHOD:   setup()
// PURPOSE:  To Setup Arduino output Pins
// PARAMS:   void
// RETURN:  void
//********************************************************
void setup() {

  // set the motor pins as outputs:
  // set all chips to enabled state
  
  // shoulder motor
  pinMode(shoulderMotorPin1, OUTPUT);
  pinMode(shoulderMotorPin2, OUTPUT);
  pinMode(shoulderMotorEnablePin, OUTPUT);
  digitalWrite(shoulderMotorEnablePin, HIGH);

  // base motor  
  pinMode(baseMotorPin1, OUTPUT);
  pinMode(baseMotorPin2, OUTPUT);
  pinMode(baseMotorEnablePin, OUTPUT);
  digitalWrite(baseMotorEnablePin, HIGH);
  
  // wrist motor
  pinMode(wristMotorPin1, OUTPUT);
  pinMode(wristMotorPin2, OUTPUT);
  pinMode(wristMotorEnablePin, OUTPUT);
  digitalWrite(wristMotorEnablePin, HIGH);
  
  // elbow motor
  pinMode(elbowMotorPin1, OUTPUT);
  pinMode(elbowMotorPin2, OUTPUT);
  pinMode(elbowMotorEnablePin, OUTPUT);
  digitalWrite(elbowMotorEnablePin, HIGH);
  
  // hand motor
  pinMode(handMotorPin1, OUTPUT);
  pinMode(handMotorPin2, OUTPUT);
  pinMode(handMotorEnablePin, OUTPUT);
  digitalWrite(handMotorEnablePin, HIGH);

  // Set BlueTooth BaudRate
  Serial.begin(baudRate);
  
  // REGISTER FUNCTIONS CALL BACK IN PHONE OBJECT (AMARINO)
  phone.registerFunction(setMotorBaseLeft,'a');       // CALL BACK FOR BASE MOTOR LEFT: Register Call Back setMotorBaseLeft
  phone.registerFunction(setMotorBaseRight,'b');      // CALL BACK FOR BASE MOTOR RIGHT: Register Call Back setMotorBaseRight
  
  
  phone.registerFunction(setMotorShoulderUp,'c');     // CALL BACK FOR SHOULDER MOTOR UP: Register Call Back setMotorShoulderUp
  phone.registerFunction(setMotorShoulderDown,'d');   // CALL BACK FOR SHOULDER MOTOR DOWN: Register Call Back setMotorShoulderDown
  
  
  phone.registerFunction(setMotorElobowUp,'e');       // CALL BACK FOR SHOULDER MOTOR DOWN: Register Call Back setMotorElobowUp
  phone.registerFunction(setMotorElbowDown,'f');      // CALL BACK FOR SHOULDER MOTOR DOWN: Register Call Back setMotorElbowDown
  
  
  phone.registerFunction(setMotorWristUp,'g');        // CALL BACK FOR SHOULDER MOTOR DOWN: Register Call Back setMotorWristUp
  phone.registerFunction(setMotorWristDown,'h');      // CALL BACK FOR SHOULDER MOTOR DOWN: Register Call Back setMotorWristDown
  
  
  phone.registerFunction(setMotorHandOpen,'i');        // CALL BACK FOR SHOULDER MOTOR DOWN: Register Call Back setMotorWristUp
  phone.registerFunction(setMotorHandClose,'j');       // CALL BACK FOR SHOULDER MOTOR DOWN: Register Call Back setMotorWristUp
  
  phone.registerFunction(turnMotorsOff,'s');           // TURN MOTORS OFF

}

//******************************************************
// METHOD:   setMotorBaseLeft()
// PURPOSE:  MOVE THE BASE MOTOR TO THE LEFT SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorBaseLeft(byte ignore, byte count)
{
    digitalWrite(baseMotorPin1, LOW);
    digitalWrite(baseMotorPin2, HIGH);
}

//******************************************************
// METHOD:   setMotorBaseRight()
// PURPOSE:  MOVE THE BASE MOTOR TO THE LEFT SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorBaseRight(byte ignore, byte count)
{
    digitalWrite(baseMotorPin1, HIGH);
    digitalWrite(baseMotorPin2, LOW);
    
}

//******************************************************
// METHOD:   setMotorShoulderUp()
// PURPOSE:  MOVE THE SHOULDER MOTOR UP SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorShoulderUp(byte ignore, byte count)
{
     digitalWrite(shoulderMotorPin1, LOW);
     digitalWrite(shoulderMotorPin2, HIGH);
}

//******************************************************
// METHOD:   setMotorShoulderUp()
// PURPOSE:  MOVE THE SHOULDER MOTOR DOWN SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorShoulderDown(byte ignore, byte count)
{
     digitalWrite(shoulderMotorPin1, HIGH);
     digitalWrite(shoulderMotorPin2, LOW);
}

//******************************************************
// METHOD:   setMotorElobowUp()
// PURPOSE:  MOVE THE ELBOW MOTOR UP SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorElobowUp(byte ignore, byte count)
{
     digitalWrite(elbowMotorPin1, HIGH);
     digitalWrite(elbowMotorPin2, LOW);
}

//******************************************************
// METHOD:   setMotorElobowDown()
// PURPOSE:  MOVE THE ELBOW MOTOR DOWN SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorElbowDown(byte ignore, byte count)
{
     digitalWrite(elbowMotorPin1, LOW);
     digitalWrite(elbowMotorPin2, HIGH);
}

//******************************************************
// METHOD:   setMotorWristUp()
// PURPOSE:  MOVE THE WRIST MOTOR UP SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorWristUp(byte ignore, byte count)
{
    digitalWrite(wristMotorPin1, HIGH);
    digitalWrite(wristMotorPin2, LOW);
}
//******************************************************
// METHOD:   setMotorWristDown()
// PURPOSE:  MOVE THE WRIST MOTOR DOWN SIDE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorWristDown(byte ignore, byte count)
{
    digitalWrite(wristMotorPin1, LOW);
    digitalWrite(wristMotorPin2, HIGH);
}

//******************************************************
// METHOD:   setMotorHandOpen()
// PURPOSE:  MOVE THE HAND MOTOR OPEN
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorHandOpen(byte ignore, byte count)
{
    digitalWrite(handMotorPin1, LOW);
    digitalWrite(handMotorPin2, HIGH);
}
//******************************************************
// METHOD:   setMotorHandClose()
// PURPOSE:  MOVE THE HAND MOTOR CLOSE
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************
void setMotorHandClose(byte ignore, byte count)
{
    digitalWrite(handMotorPin1, HIGH);
    digitalWrite(handMotorPin2, LOW);
}


//******************************************************
// METHOD:   turnMotorsOff()
// PURPOSE:  TURN MOTORS OFF 
// RETURN:   void
// PARAM:    byte ignore, byte count
// *****************************************************

void turnMotorsOff(byte ignore, byte count)
{
    digitalWrite(baseMotorPin1, LOW);
    digitalWrite(baseMotorPin2, LOW);
    digitalWrite(shoulderMotorPin1, LOW);
    digitalWrite(shoulderMotorPin2, LOW);
    digitalWrite(elbowMotorPin1, LOW);
    digitalWrite(elbowMotorPin2, LOW);
    digitalWrite(wristMotorPin1, LOW);
    digitalWrite(wristMotorPin2, LOW);
    digitalWrite(handMotorPin1, LOW);
    digitalWrite(handMotorPin2, LOW);
}
//**********************************************************
// METHOD: loop()
// INITIAL  APPLICATION MAIN LOOP
// params:  void
// return:  void
//**********************************************************
void loop()
{
     phone.receive();     //LISTEN EVENTS FROM ANDROID PHONE
}

//*****************************************************************************
//*****************************************************************************
//  CHANGE LOG
//*****************************************************************************
// 04/13/2012 - EDGAR ACOSTA - INITIAL VERSION


