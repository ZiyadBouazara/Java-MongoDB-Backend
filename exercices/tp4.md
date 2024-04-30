# Exercices - TP4



































































































# 4. Sécurité Logiciel

# Description

I attempted to utilize Snyk for our security analysis. However, due to limitations in Snyk's free tier, it does not provide a token necessary for integration with our Continuous Integration (CI) pipeline. 

## Nevertheless, we are able to review vulnerabilities through Snyk's web application interface. 

<img width="1440" alt="Screenshot 2024-04-20 at 9 34 46 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/0326fb82-8e4f-4d54-b676-9dc5aad01c5f">

## Snyk is able to check dependency through :
### - the pom.xml:

<img width="1440" alt="Screenshot 2024-04-20 at 9 37 37 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/0a3d498b-b443-46a2-ad94-14f3bc828350">
<img width="1440" alt="Screenshot 2024-04-20 at 9 37 52 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/55091d66-b9bc-42b9-acf7-5b501918211c">

### - the Docker image through our DockerFile:

<img width="1440" alt="Screenshot 2024-04-20 at 9 35 00 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/b48bec45-b712-431d-ab55-16eb030b1abb">
<img width="1440" alt="Screenshot 2024-04-20 at 9 36 25 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/644302ca-367f-476e-9ca0-eeebf5ba543c">
<img width="1440" alt="Screenshot 2024-04-20 at 9 36 39 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/0a50f3aa-c071-4ca7-b8f8-9308ef463009">

### - Code analysis
<img width="1440" alt="Screenshot 2024-04-20 at 9 39 40 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/9a08bf3f-1007-4240-b700-f83843898446">

## Additionally, Snyk autonomously generates merge requests suggesting upgrades for dependencies.
<img width="1440" alt="Screenshot 2024-04-20 at 9 40 25 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/42340736-07bc-4185-880c-14b558c1667d">

## CLI integration
### We also integrated Snyk in our pipelines in order to check for vulnerabilities during CI : 
[
<img width="1920" alt="Screenshot 2024-04-30 at 1 55 07 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/a4042dff-107f-492d-aedb-2e79608a9a8c">
](url)

### Here is what the CLI redirects us to in order to see more details about the vulnerability.

<img width="1920" alt="Screenshot 2024-04-30 at 1 54 42 PM" src="https://github.com/glo2003-h24-eq28/restalo-h24-eq28/assets/96509783/bb3dedf3-2495-4423-a0ae-9df90961b68a">

