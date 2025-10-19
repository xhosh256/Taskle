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

export async function fetchAllTasks(auth) {
  const res = await fetch(`${API}/tasks?size=1000000`, {
    method: 'GET',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    throw new Error('Something went wrong');
  }

  const tasks = await res.json();
  return tasks.content;
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