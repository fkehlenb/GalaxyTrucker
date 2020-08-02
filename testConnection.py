import socket
import time
import threading


def bind_to_addr(addr,port):
    s = socket.socket()
    s.bind((addr,port))
    s.listen(True)
    a,b = s.accept()
    a.close()
    print("Connection successful!")
    time.sleep(5)
    exit()


def connect_to_addr(addr,port):
    s = socket.socket()
    try:
        s.connect((addr,port))
    except Exception:
        print("Server unreachable!")
        time.sleep(5)
        exit
    
    
addr = input("Geben sie eine Adresse ein um zu Testen ob diese sichtbar ist!\n#> ")
port = input("Geben sie den Port ein!\n#> ")

try:
    port = int(port)
except Exception:
    print("Ungültiger Port!")
    time.sleep(5)
    exit()

t = threading.Thread(target = bind_to_addr, args = (addr,port))
t.start()
c = threading.Thread(target = connect_to_addr, args = (addr,port))
c.start()
    
