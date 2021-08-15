#include "LiquidCrystal.h"
LiquidCrystal lcd(8, 9, 4, 5, 6, 7);
//rs, enable pin, D4, D5,D6 ,D7 ,R/W ,VSS ,Vcc
#define Offset 0.2

void sortLargeToSmall(int);
int average(int);

//float input_voltage;   
unsigned long int avgValue;  //Store the average value of the sensor feedback
int buf[10];
 
void setup()
{
  //Serial.begin(9600);  
  lcd.begin(16, 2);
  lcd.print("PHTest");
  delay(1000);
}
void loop()
{
  for(int i=0;i<10;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    Serial.begin(9600);
    buf[i]=analogRead(0);
    delay(10);
  }

  //int analog_value = analogRead(1);
  //input_voltage = (analog_value * 5.0) / 1024.0; 

  sortLargeToSmall(buf);
  avgValue = average(buf);

  float phValue=(float)avgValue*5.0/1024; //convert the analog into millivolt
  phValue=(3.5*phValue) + Offset;                      //convert the millivolt into pH value
  

  lcd.setCursor(0, 1);
  lcd.print("PH:");
  lcd.print(phValue);
   
  delay(800);
}

void sortLargeToSmall(int list[])
{
  int temp;
  for(int i=0;i<9;i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<10;j++)
    {
      if(list[i]>list[j])
      {
        temp=list[i];
        list[i]=list[j];
        list[j]=temp;
      }
    }
  }
}

int average(int list[])
{
  int av=0;
  for(int i=2;i<8;i++)                      //take the average value of 6 center sample
  {  
    av+=list[i];
  }
  return (av/6);
}
