FROM python:3
WORKDIR /usr/src/app
COPY data/* .
RUN pip install --no-cache-dir -r requirements.txt
CMD [ "python", "./main.py" ]

