import { API } from "../config/properties.js";
import { renderLoginPage } from "../render/auth.js";
import { tasksPaginationAndFilter } from "./home.js";

export async function handleLogin(username, password) {
  const res = await fetch(`${API}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  });

  if (!res.ok) {
    window.alert("Неверные логин или пароль");
    return;
  }

  const auth = await res.json();
  localStorage.setItem('auth', JSON.stringify(auth));

  tasksPaginationAndFilter(0, {"title": ''});
}

export async function handleRegister(account) {
  const res = await fetch(`${API}/auth/register`, {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(account)
  });

  if(!res.ok) {
    throw new Error('Something went wrong!');
  }

  const data = await res.json();
  console.log(data);

  renderLoginPage();
}