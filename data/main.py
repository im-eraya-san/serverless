from flask import Flask, render_template
import psycopg2
import os, socket

app = Flask(__name__)

DB_CONFIG = {
    "host": os.getenv("DB_HOST"),
    "port": os.getenv("DB_PORT"),
    "dbname": os.getenv("DB_NAME"),
    "user": os.getenv("DB_USER"),
    "password": os.getenv("DB_PASSWD"),
    "connect_timeout": 5,
}

app_port = os.getenv("APP_PORT")

def sqlConnection():
    try:
        with psycopg2.connect(**DB_CONFIG) as conn:
            with conn.cursor() as cur:
                cur.execute("SELECT version();")
                version = cur.fetchone()[0]
        print("Connected successfully!")
        print("PostgreSQL version:", version)
        return True
    except psycopg2.OperationalError as e:
        print("Connection failed:", e)
        return False

def getOsInfo():
    soc = socket.gethostname()
    return f"Server Name: {soc} \n Ipv4 Address: {socket.gethostbyname(soc)}"

@app.route("/")
def main():
   _result = sqlConnection()
   #sn = getOsInfo()
   sn = ""
   if _result:
       return render_template('index.html',result='Database connected',srvName=sn)
   else:
       return render_template('index.html',result='Database not connected',srvName=sn)
       

if  __name__ == "__main__":
        app.run(host="0.0.0.0",port=5000)
