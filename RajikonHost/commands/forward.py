import wiringpi as pi,sys

MOTOR1 = 27
MOTOR2 = 17

args = sys.argv

per = int(args[1])

pi.wiringPiSetupGpio()
pi.pinMode(MOTOR1, pi.OUTPUT)
pi.pinMode(MOTOR2, pi.OUTPUT)

pi.softPwmCreate(MOTOR1, 0, 100)

pi.digitalWrite(MOTOR1, pi.LOW)
pi.digitalWrite(MOTOR2, pi.LOW)

pi.softPwmWrite(MOTOR1, per)

print("forward")
