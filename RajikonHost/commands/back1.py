import wiringpi as pi, sys

MOTOR3 = 11
MOTOR4 = 9

args = sys.argv

per = int(args[1])

pi.wiringPiSetupGpio()
pi.pinMode(MOTOR3, pi.OUTPUT)
pi.pinMode(MOTOR4, pi.OUTPUT)

pi.softPwmCreate(MOTOR3, 0, 100)
pi.softPwmCreate(MOTOR4, 0, 100)

pi.digitalWrite(MOTOR3, pi.LOW)
pi.digitalWrite(MOTOR4, pi.LOW)

pi.digitalWrite(MOTOR3, pi.LOW)
pi.softPwmWrite(MOTOR4, per)

print("back")