# Vigenère cipher Android App

## Introduction
This android app **Vigenère cipher** aims at being a simple tool to encrypt or decrypt an alphabetic text based on [Vigenère cipher](https://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher) method. This document is the user manual of this Android app.

## Getting Started
1. In Android Studio, click **Import project**, select the dir where you cloned the repo, and click **OK**.
2. Android Target Version was set **API level 23**. In the Android Studio, install Nexus 5X API Level 23.

## Feature
There are two types of functions based on Vigenère cipher algorithm implemented:
- **Encrypt function:**
	the App will encrypt the Text input by the Key Phrase, and show the Result Message, when the run button is pressed.
- **Decrypt function:**
	the App will decrypt the Text input by the Key Phrase, and show the Result Message, when the run button is pressed.

## Running the tests

> **Encrypt function demo:**

|Text            |Key Phrase         |Result Message        |
|----------------|-------------------|----------------------|
|ApPlE           |`cat`              |'CpInE'               |
|dOg4cAt         |`banana`           |"eOt4cNau"            |

> **Decrypt function demo:**

|Text            |Key Phrase         |Result Message        |
|----------------|-------------------|----------------------|
|CpInE           |`cat`              |'ApPlE'               |
|eOt4cNau        |`banana`           |"dOg4cAt"             |

## Demo
Here are demos for the App.
### Encrypt and Decrypt
<img src="https://github.gatech.edu/gt-omscs-se-2018spring/6300Spring18jcen8/blob/master/Assignment4/App%20Screenshot/Screenshot_1517282202.png" width="200" />                        <img src="https://github.gatech.edu/gt-omscs-se-2018spring/6300Spring18jcen8/blob/master/Assignment4/App%20Screenshot/Screenshot_1517282256.png" width="200" />

### Applicable errors in the text input
Applicable error marks will be displayed when the "RUN" button is clicked:
1. A Text with no alphabetic characters;
2. Key Phrase that contains non-alphabetic characters;
3. An empty Key Phrase.
Examples are shown below:

<img src="https://github.gatech.edu/gt-omscs-se-2018spring/6300Spring18jcen8/blob/master/Assignment4/App%20Screenshot/Screenshot_1517282318.png" width="200" />                        <img src="https://github.gatech.edu/gt-omscs-se-2018spring/6300Spring18jcen8/blob/master/Assignment4/App%20Screenshot/Screenshot_1517282418.png" width="200" />

<img
src="https://github.gatech.edu/gt-omscs-se-2018spring/6300Spring18jcen8/blob/master/Assignment4/App%20Screenshot/Screenshot_1517282438.png" width="200" />                        <img src="https://github.gatech.edu/gt-omscs-se-2018spring/6300Spring18jcen8/blob/master/Assignment4/App%20Screenshot/Screenshot_1517282450.png" width="200" />

## Authors
- Jiajie Cen

## Acknowledgment
- Georgia Tech OMSCS CS6300
