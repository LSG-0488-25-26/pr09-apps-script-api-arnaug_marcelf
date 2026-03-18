# Projecte DAM2 0488: Apps Script API – Control de Plagues

## Descripció
Aquest projecte és una aplicació Android desenvolupada amb **Jetpack Compose** que es connecta a una **API feta amb Google Apps Script** per gestionar dades sobre plagues. Les dades originals provenen d’un dataset CSV de Kaggle.  

La app permet veure totes les plagues registrades, afegir-ne de noves i gestionar el login i registre d’usuaris.

---

## Funcionalitats

### Google Sheets + Apps Script API
- Dades de plagues organitzades en diferents pestanyes del Google Sheets.
- API amb **JavaScript** dins d’Apps Script.
- Implementació de **doGet(e)** i **doPost(e)** per:
  - Retornar dades amb GET.
  - Inserir noves dades amb POST.
- **Mínim 3 endpoints** disponibles per diferents consultes.
- Filtrat de dades segons tipus de plaga i el segle.
- Protegida amb **API_KEY** com a variable d’entorn.

### App Android
- Desenvolupada amb **Jetpack Compose** i arquitectura **MVVM**.
- **Login i registre d’usuaris**, amb credencials guardades en un XML a través de **Shared Preferences**.
- Consum de l’API per:
  - Mostrar totes les plagues amb GET.
  - Filtratge de plagues amb type GET.
  - Afegir noves plagues amb POST.
- Ús de variables d’entorn **API_KEY** i **BASE_URL** a `secrets.properties`.
- Visualització de dades amb composables Lazy.

---

## Tecnologies
- **Android**: Kotlin, Jetpack Compose, MVVM, LiveData, Retrofit.
- **Backend**: Google Apps Script (JavaScript), Google Sheets.
- **Persistència local**: Shared Preferences (XML).
- **Dataset**: CSV de plagues de Kaggle.

---

## Captures i vídeo
S’inclouen captures de les diferents pantalles de la app que mostren:
1. Login i registre d’usuari.
  <img width="336" height="731" alt="image" src="https://github.com/user-attachments/assets/07993a1b-77c8-4798-86dd-6275e686cd17" />
  <img width="336" height="731" alt="image" src="https://github.com/user-attachments/assets/e9f0296f-1dae-4c55-b6db-c87eb13aff37" />

---
2. Visualització de plagues.
  <img width="336" height="731" alt="image" src="https://github.com/user-attachments/assets/5a1d989a-f486-4716-b19c-4f51a0ae91eb" />
  <img width="336" height="731" alt="image" src="https://github.com/user-attachments/assets/27416292-ad6d-4884-90f8-c7ac9a42fa8f" />

---
3. Formulari per afegir noves plagues.
  <img width="336" height="731" alt="image" src="https://github.com/user-attachments/assets/a01c1427-45c2-471e-8280-e3fb27b096b4" />

---
4. Filtratge de plagues
  <img width="336" height="731" alt="image" src="https://github.com/user-attachments/assets/abadd750-0a8b-45ce-ac9e-d5c5f0bbc4f1" />

---
5. Info de l'usuari
  <img width="336" height="731" alt="image" src="https://github.com/user-attachments/assets/1cceb3f5-bc96-4c46-bb5e-ebf7824ea61b" />

---
També s’inclou un vídeo de demostració que mostra el flux complet: login, consulta de dades, inserció de noves plagues i actualització en Google Sheets.
Link Video: 
