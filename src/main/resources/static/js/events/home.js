import { API } from "../config/properties.js";
import { noSuchTasks, renderHomePage } from "../render/home.js";

export async function tasksPaginationAndFilter(page, filter) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  const query = new URLSearchParams(filter);
  const res = await fetch(`${API}/tasks?page=${page}&${query.toString()}`, {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${auth.token}` }
  })


  if(!res.ok) {
    noSuchTasks();
    return;
  }

  const data = await res.json();
  renderHomePage(data);
}

export async function createTask(task) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  const res = await fetch(`${API}/tasks`, {
    method: 'POST',
    headers: {
      'Authorization' : `Bearer ${auth.token}`,
      'Content-Type' : 'application/json'
    },

    body: JSON.stringify(task)
  });

  if(!res.ok) {
    throw new Error('Something went wrong!');
  }

  const data = await res.json();
  console.log(data);

  tasksPaginationAndFilter(0, { "title": '', 'tags': [] });
}

export async function handleEdit(id, task) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  const res = await fetch(`${API}/tasks/${id}`, {
    method: 'PUT',
    headers: {
      'Authorization': `Bearer ${auth.token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(task)
  });

  if(!res.ok) {
    throw new Error('Something went wrong');
  }

  tasksPaginationAndFilter(0, { "title": '', 'tags': [] });
}