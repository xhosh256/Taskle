import { API } from "../config/properties.js";


export async function checkAuth() {
  const auth = JSON.parse(localStorage.getItem("auth"));

  if (auth == null) {
    return false;
  }

  const res = await fetch(`${API}/users/me`, {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${auth.token}` }
  });

  if (!res.ok) {
    return false;
  }

  return true;
}

export async function getUserInfo() {
  const auth = JSON.parse(localStorage.getItem('auth'));

  const res = await fetch(`${API}/users/me`, {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${auth.token}` }
  });

  if (!res.ok) {
    throw new Error('Something went wrong!');
  }
  const userInfo = await res.json();

  return userInfo;
}