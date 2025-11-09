import { API } from "../config/properties.js";

export async function fetchTaskById(id) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  const res = await fetch(`${API}/tasks/${id}`, {
    method: 'GET',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    throw new Error('Something went wrong');
  }

  const data = await res.json();
  return data;
}

export async function fetchAllTasks(auth, id) {
  const res = await fetch(`${API}/groups/${id}/tasks`, {
    method: 'GET',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    throw new Error('Something went wrong');
  }

  const tasks = await res.json();
  return tasks;
}

export async function deleteTask(id) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  const res = await fetch(`${API}/tasks/${id}`, {
    method: 'DELETE',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    throw new Error('Something went wrong');
  }

  return true;
}

export async function getRemain() {
  const auth = JSON.parse(localStorage.getItem('auth'));

  const res = await fetch(`${API}/tasks/remain`, {
    method: 'GET',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    console.error("Something went wrong");
  }

  const data = await res.json();
  return data.count;
}