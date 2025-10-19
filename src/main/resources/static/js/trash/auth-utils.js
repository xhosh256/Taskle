import { renderHomePage } from "./home.js";

const API = 'http://localhost:8080/api/v1';

export async function executeLogin(e) {
  e.preventDefault();

  try {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const res = await fetch(`${API}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, password })
    });

    if (!res.ok) {
      throw new Error("Неверный логин или пароль");
    }

    const auth = await res.json();
    localStorage.setItem('auth', JSON.stringify(auth));

    renderHomePage();
  } catch (err) {
    console.error(err);
  }
}

export async function executeRegister(e) {
  e.preventDefault();

  try {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const firstname = document.getElementById('firstname').value;
    const lastname = document.getElementById('lastname').value;

    const res = await fetch(`${API}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ 
        username, 
        password,
        firstname,
        lastname
       })
    });

    if(!res.ok) {
      throw new Error("Username занят");
    }

  } catch (err) {
    throw err;
  }
} 