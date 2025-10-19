import { API } from "../config/properties.js";
import { renderGroup } from "../render/group.js";

export async function fetchCreateGroup(auth, group) {
  const res = await fetch(`${API}/groups`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${auth.token}`,
      'Content-Type': 'application/json'
    },

    body: JSON.stringify(group)
  });

  if (!res.ok) {
    const errorBody = await res.json().catch(() => ({}));
    console.error(errorBody.message);
    return;
  }

  return await res.json();
}

export async function fetchGroups(auth) {
  const res = await fetch(`${API}/groups`, {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${auth.token}` }
  });

  if (!res.ok) {
    const errorBody = await res.json().catch(() => ({}));
    console.error(errorBody.message);
    return;
  }

  const data = await res.json();

  return data.groups;
}

export async function fetchGroup(auth, id) {
  const res = await fetch(`${API}/groups/${id}`, {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${auth.token}` }
  });

  if (!res.ok) {
    const errorBody = await res.json().catch(() => ({}));
    console.error(errorBody);
    return;
  }

  const groupReadDto = await res.json();
  return groupReadDto;
}

export async function fetchDeleteGroup(auth, id) {
  const res = await fetch(`${API}/groups/${id}`, {
    method: 'DELETE',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    const data = await res.json().catch(() => {});
    console.error(data.status, data.message);
    return;
  }

  const data = await res.json().catch(() => {});
  console.log(data.message);
}

export async function addTaskToGroup(auth, taskId, groupId) {
  const res = await fetch(`${API}/groups/${groupId}/tasks/${taskId}`, {
    method: 'POST',
    headers: {'Authorization': `Bearer ${auth.token}`}
  });

  if(!res.ok) {
    throw new Error("Something went wrong");
  }

  const data = await res.json();
  console.log(data.message);

  renderGroup(auth, groupId);
}