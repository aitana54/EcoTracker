
# EchoTracker - Descripció del Projecte

## 📌 Descripció General
**EchoTracker** és una aplicació desenvolupada en el marc del mòdul *Sostenibilitat aplicada al desenvolupament tecnològic*, amb l'objectiu d'ajudar a les empreses a fer el seguiment del seu impacte ambiental. 

L'aplicació facilita el **registre i gestió d'activitats sostenibles** realitzades per les empreses, i calcula de manera estimada la **reducció d'emissions de CO₂** associada a aquestes accions. Així, les organitzacions poden tenir una visió clara del seu compromís amb la sostenibilitat i el medi ambient.

## 🎯 Finalitat
- **Promoure la sostenibilitat** empresarial mitjançant una eina accessible.
- **Quantificar l'impacte positiu** de les accions sostenibles (com ara reducció de residus, eficiència energètica, etc.).
- **Conscienciar** sobre la importància de la reducció de l'empremta de carboni.

## 💡 Funcionalitats Bàsiques
- Registre d'activitats sostenibles (ex: reciclatge, ús d'energies renovables).
- Càlcul automàtic de l'estalvi en emissions de CO₂.
- Visualització de dades i estadístiques d'impacte ambiental.
- Emmagatzematge persistent de les dades per a un seguiment continu.

## ✨ Funcionalitat Afegida pel Grup
- **Sistema de Login i Registre d'Usuaris**  
  Per garantir que cada empresa pugui gestionar les seves dades de manera segura i personalitzada, s'ha implementat:
  - **Registre** d'usuaris amb credencials úniques (email i contrasenya).
  - **Inici de sessió** amb autenticació.
  - Accés diferenciat per a cada usuari (les dades es guarden de manera individualitzada).

## 🛠 Tecnologies i Competències Aplicades
- **Programació orientada a objectes** (disseny modular i escalable).
- **Persistència de dades** (emmagatzematge local o en bases de dades).
- **Disseny d'interfícies gràfiques** (UX/UI per a una bona experiència d'usuari).
- **Eines professionals de desenvolupament** (IDE, control de versions, etc.).
- **Criteris ambientals i de qualitat de codi** (eficiència i sostenibilitat en el propi desenvolupament).

## 🌍 Impacte Esperat
L'aplicació no només contribueix a la **transformació digital de les empreses**, sinó que també les involucra activament en la **lluita contra el canvi climàtic**, alineant-se amb objectius com els ODS (Objectius de Desenvolupament Sostenible) de l'ONU.

## 🏗️ Estructura del Projecte

### 1. Arquitectura Principal
**Únic controlador** (`HelloController.java`) que gestiona tot el flux:
```java
public class HelloController implements Initializable {
    // Gestiona navegació, BD, autenticació i lògica d'activitats
}
```
## 🔑 Components Clau

| Component          | Descripció                                  | Mètodes/Fitxers Associats            |
|--------------------|---------------------------------------------|---------------------------------------|
| **🧭 Navegació**   | Gestiona el canvi entre pantalles           | `formswitch()`, `sceneBuild()`        |
| **🔐 Autenticació**| Controla login i registre d'usuaris         | `LOGIN()`, `register()`               |
| **🌱 Activitats**  | Gestiona accions sostenibles i càlcul CO₂   | `store_data_in_db()`                  |
| **📊 Visualització**| Mostra gràfics i llistats d'activitats      | `co2_data_chart()`, `show_act_config()`|

## 💾 Esquema de la Base de Dades

![Imagen de WhatsApp 2025-05-22 a las 21 19 19_8d3eb5f6](https://github.com/user-attachments/assets/593322c0-a728-4f79-b2db-d51cc4db8722)


## 📊 Descripció Tècnica del Càlcul de CO₂

- **Fórmula aplicada**:
```Text
  CO₂ estalviat = quantitat_introduïda * valor_per_unitat
```

Aquest càlcul es realitza internament en el moment d’inserir l’activitat a la base de dades. Es fa servir una subconsulta SQL que multiplica la quantitat per l'estalvi associat a la categoria:

| Activitat          | Valor CO₂ estalviat per unitat      |
|--------------------|-----------------------------|
| Ciclisme           | 0.24 kg CO₂/km              |
| Teletreball        | 0.225 kg CO₂/hora           |
| Reciclatge paper   | 1.0 kg CO₂/kg               |
| Estalvi llum       | 0.15 kg CO₂/hora            |


## 📘 Manual d'usuari


### 🟢 Pas 1: Obrir l'aplicació
Obre l’aplicació des del teu dispositiu o entorn de treball.

![imagen](https://github.com/user-attachments/assets/be222e24-bfc3-4bb7-9958-9f4eb70a0629)

---

### 🔐 Pas 2: Iniciar sessió o registrar-se

- Si ja tens un compte, **introdueix les teves credencials per iniciar sessió**.
- Si no tens compte, fes clic al botó **"Registre"**:
  - Introdueix les credencials necessàries per crear un usuari nou.
  - Un cop registrat, podràs accedir al sistema amb el teu nou compte.

![imagen](https://github.com/user-attachments/assets/593f763f-328d-4513-8fcb-530b330fd330)

---

### 📋 Pas 3: Consultar activitats (Activity Display)

Un cop dins del menú principal: 

![imagen](https://github.com/user-attachments/assets/c8e092ae-b9e0-45e8-8efa-01d4477638fc)

- Entra a **`Activity Display`**.
- Dins d’aquest formulari trobaràs **totes les activitats registrades** a la base de dades 🗃️.
- Funcions disponibles:
  - 📤 **Exportar les activitats** a format `.csv` o `.txt`.
  - 📉 A la part inferior esquerra, veuràs **l’estalvi total de CO₂** generat per totes les activitats.
![imagen](https://github.com/user-attachments/assets/3907e7a5-5fb3-477d-9fd2-e71be3fc9015)

---

### 🛠️ Pas 4: Gestió d'activitats (Activities)

Des del menú principal, entra a **`Activities`**. Aquí trobaràs dos subapartats:

![imagen](https://github.com/user-attachments/assets/de3e3f77-564b-4da5-89d0-0a350448c5a0)
#### ➕ Afegir nova activitat

1. Introdueix:
   - **Nom de l’activitat**
   - **Categoria** (ex: ciclisme, teletreball...)
   - **Quantitat** (en KM, KG o Hores, segons l'activitat)

![imagen](https://github.com/user-attachments/assets/9dbd837b-7e2f-4db7-af06-7897f7f4561e)

2. Fes clic a **"Guardar"** per enregistrar l’activitat a la base de dades.

#### 🗑️ Esborrar una activitat

1. Veuràs una taula amb totes les activitats registrades.
2. A la part inferior trobaràs un **`ChoiceBox`**:
   - Introdueix la **ID de l’activitat** que vols eliminar.
   - Prem el botó **"Delete"** per esborrar-la de la base de dades.

![imagen](https://github.com/user-attachments/assets/dd421cf5-ed8a-4dfa-a9bd-470d8af720b3)


---

### 📊 Pas 5: Consultar gràfic de CO₂ (CO₂ Chart)

Des del menú principal, entra a **`CO₂ Chart`**:

- Es mostrarà una taula amb el **valor de CO₂ estalviat per unitat** (kg/km/h), depenent de la categoria de cada activitat.
- Aquesta secció permet visualitzar de forma clara l’impacte ambiental positiu de les accions realitzades. 🌱
![imagen](https://github.com/user-attachments/assets/6af3a843-1b0a-4d68-a802-40d990ae4707)

---

## Pla de proves (Testing)

### ✅ Enfocament del pla de proves

S'ha implementat un **Master Test Plan** amb enfocament **unitari** i **funcional**, utilitzant **JUnit 5** i **Mockito** per assegurar el correcte funcionament de l'aplicació. Les proves abasten des de la connexió a la base de dades fins a la validació de formularis i la navegació entre pantalles.

---

### 📋 Llistat de proves realitzades

| # | Què es prova                                | Mètode o acció                             | Resultat esperat                                                                 |
|---|---------------------------------------------|--------------------------------------------|----------------------------------------------------------------------------------|
| 1 | Connexió a la base de dades                 | `connect_to_bd()`                          | Connexió oberta i no null                                                        |
| 2 | Generació del gràfic HTML                   | `generateCO2ChartHtml()`                   | HTML generat no null ni buit                                                     |
| 3 | Contingut del gràfic HTML                   | `generateCO2ChartHtml()`                   | Conté "Ciclisme", "0.24", "Reciclar 1 kg de paper"                               |
| 4 | Navegació entre formularis                  | Canvi entre `Activity Display`, `Activities`, `CO₂ Chart` | Transició sense errors ni bloqueigs |
| 5 | Validació de formulari d'activitat nova     | Intent de guardar amb camps buits          | Es mostra un missatge d'error a la pantalla                                      |
| 6 | Esborrar activitat amb ID vàlida            | Selecció d'ID i clic a "Delete"            | L'activitat és eliminada de la base de dades                                     |
| 7 | Esborrar activitat amb ID buida o invàlida  | No selecció d'ID i clic a "Delete"         | Es mostra un missatge d'error i no s'executa l'eliminació                        |

---

### 🧰 Eines utilitzades

- **JUnit 5** (`org.junit.jupiter.api`)
- **Mockito** (`org.mockito.Mockito`)
- **Assertions**:
  - `assertNotNull`
  - `assertFalse`
  - `assertTrue`
  - `fail`

---

### 📁 Fitxers de proves

Les proves es troben al fitxer:

📄 [MasterTestPlan.xlsx](https://docs.google.com/spreadsheets/d/14GX5KtxyF2-eahTYYHXvgn22NEiHAXj9/edit?usp=sharing&ouid=116899940093399257182&rtpof=true&sd=true)

