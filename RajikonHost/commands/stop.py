import wiringpi as pi, sys

MOTOR1 = 4
MOTOR2 = 17

MOTOR3 = 11
MOTOR4 = 9

LEFT_STOP = 0
RIGHT_STOP = 1
BOTH_STOP = 2

f = open("test.txt", "w")
f.write("yo bitch!")
f.close()

args = sys.argv

pi.wiringPiSetupGpio()
pi.pinMode(MOTOR1, pi.OUTPUT)
pi.pinMode(MOTOR2, pi.OUTPUT)
pi.pinMode(MOTOR3, pi.OUTPUT)
pi.pinMode(MOTOR4, pi.OUTPUT)

mode = int(args[1])

if(mode == LEFT_STOP):
    pi.digitalWrite(MOTOR1, pi.LOW)
    pi.digitalWrite(MOTOR2, pi.LOW)
elif(mode == RIGHT_STOP):
    pi.digitalWrite(MOTOR3, pi.LOW)
    pi.digitalWrite(MOTOR4, pi.LOW)
elif(mode == BOTH_STOP):
    pi.digitalWrite(MOTOR1, pi.LOW)
    pi.digitalWrite(MOTOR2, pi.LOW)
    pi.digitalWrite(MOTOR3, pi.LOW)
    pi.digitalWrite(MOTOR4, pi.LOW)
else:
    pi.digitalWrite(MOTOR1, pi.LOW)
    pi.digitalWrite(MOTOR2, pi.LOW)
    pi.digitalWrite(MOTOR3, pi.LOW)
    pi.digitalWrite(MOTOR4, pi.LOW)

print("stop")