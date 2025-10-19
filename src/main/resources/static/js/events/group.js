import { addTaskToGroup, fetchCreateGroup, fetchDeleteGroup } from "../api/group.js";
import { API } from "../config/properties.js";
import { renderGroup } from "../render/group.js";
import { tasksPaginationAndFilter } from "./home.js";

export async function createGroup(group) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  await fetchCreateGroup(auth, group);

  await tasksPaginationAndFilter(0, { "title": '', 'tags': [] });
}

export async function deleteGroup(id) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  await fetchDeleteGroup(auth, id);
  await tasksPaginationAndFilter(0, { "title": '', 'tags': [] });
}

export async function getAllChecked(id) {
  const form = document.getElementById('add-tasks-form');

  const tasksToAdd = [];
  const checkboxes = Array.from(form.querySelectorAll('input[type="checkbox"]'));
  for(const checkbox of checkboxes) {
    if(checkbox.checked) {
      tasksToAdd.push(checkbox.dataset.id);
    }
  }

  await addAllTasks(tasksToAdd, id);
}

async function addAllTasks(taskIds, groupId) {
  const auth = JSON.parse(localStorage.getItem('auth'));

  for(const taskId of taskIds) {
    await addTaskToGroup(auth, taskId, groupId);
  }
}

export async function removeTaskFromGroup(auth, taskId, groupId) {
  const res = await fetch(`${API}/groups/${groupId}/tasks/${taskId}`, {
    method: 'DELETE',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    throw new Error("Something went wrong");
  }

  const data = await res.json();
  console.log(data.message);

  renderGroup(auth, groupId);
}