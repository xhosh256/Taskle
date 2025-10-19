import { closeByClick, renderLogout, renderNameDropdown, renderTasksDropdown } from "./home-utils.js";
import { renderEditOwnProfile, renderOwnProfile } from "./profile-utils.js";

const API = 'http://localhost:8080/api/v1';
const app = document.getElementById('app');

export function renderHomePage() {
  document.title = 'Welcome to Taskle';

  const auth = JSON.parse(localStorage.getItem('auth'));

  app.innerHTML =
    `
    <header>
      <button id="logo-btn">Taskle</button>

      <div style="display: flex; align-items: center; gap: 25px">

        <div id="tasks-container">
          Tasks
          <ul id="tasks-dropdown">
            <li>Open tasks menu</li>
            <li>Create new task</li>
          </ul>
        </div>

        <div id="name-container">
          ${auth.firstname} ${auth.lastname}
          <ul id="name-dropdown">
            <li id="profile-link">Profile</li>
            <li id="settings-link">Settings</li>
            <li id="logout">Log out</li>
          </ul>
        </div>

      </div>

    </header>
  `;

  document.getElementById('name-container').addEventListener('click', renderNameDropdown);
  document.getElementById('tasks-container').addEventListener('click', renderTasksDropdown);
  document.addEventListener('click', closeByClick);

  document.getElementById('profile-link').addEventListener('click', async (e) => {
    const auth = JSON.parse(localStorage.getItem('auth'));

    const res = await fetch(`${API}/users/${auth.username}`, {
      method: 'GET',
      headers: { 'Authorization': `Bearer ${auth.token}` }
    });

    if (!res.ok) {
      throw new Error("Something went wrong");
    }

    const userReadDto = await res.json();
    renderOwnProfile(userReadDto);
  });
  document.getElementById('settings-link').addEventListener('click', async () => {
    renderEditOwnProfile();

    document.getElementById('save').addEventListener('click', async () => {
      const auth = JSON.parse(localStorage.getItem('auth'));

      const firstname = document.getElementById('firstname').value;
      const lastname = document.getElementById('lastname').value;

      const res = await fetch(`${API}/users/${auth.username}`, {
        method: 'PUT',
        headers: { 'Authorization': `Bearer ${auth.token}`, 'Content-Type': 'application/json' },
        body: JSON.stringify({ firstname, lastname })
      });

      if (!res.ok) {
        throw new Error("Something went wrong");
      }

      const userReadDto = await res.json();
      renderOwnProfile(userReadDto);
    });
  });
  document.getElementById('logout').addEventListener('click', renderLogout);
}