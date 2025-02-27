## DU4 - Exercise 10 - Download images from a webservice and upload to a SFTP server

Create a program that fetches a random set of images (based on a random page number) from the Picsum API (https://picsum.photos/), downloads these images concurrently, and then uploads them to an SFTP server. After uploading, the previously downloaded image files are deleted locally.

To stablish the SSH connection you must use a private key,  open an SFTP channel, upload a file from the local directory to the remote server, and then close all connections.


--


## DU4 - Ejercicio 10 - Descarga de imágenes desde un servicio web y subida a un servidor SFTP

Cree un programa que obtenga un conjunto aleatorio de imágenes (basado en un número de página aleatorio) de la API de Picsum (https://picsum.photos/), descargue estas imágenes simultáneamente, y luego las suba a un servidor SFTP. Tras la carga, los archivos de imagen descargados anteriormente se eliminan localmente.

Para establecer la conexión SSH debes utilizar una clave privada, abrir un canal SFTP, subir un archivo del directorio local al servidor remoto y, a continuación, cerrar todas las conexiones.



--

Ejemplo: 

https://picsum.photos/v2/list?page=5

Según el número te devuelve una lista de imágenes diferente.


"download_url": "https://picsum.photos/id/25/5000/3333