import {renderLoginPage} from "./auth.js";

export function renderNameDropdown(e) {
  e.preventDefault();
  const nameDropdown = document.getElementById('name-dropdown');
  nameDropdown.style.display = nameDropdown.style.display === 'block' ? 'none' : 'block';
}

export function renderTasksDropdown(e) {
  e.preventDefault();
  const tasksDropdown = document.getElementById('tasks-dropdown');
  tasksDropdown.style.display = tasksDropdown.style.display === 'block' ? 'none' : 'block';
}

export function closeByClick(e) {
  const nameDropdown = document.getElementById('name-dropdown');
  const tasksDropdown = document.getElementById('tasks-dropdown');
  const nameContainer = document.getElementById('name-container');
  const tasksContainer = document.getElementById('tasks-container');

  const target = e.target;

  if (!nameContainer.contains(target) && target.id != 'name-dropdown') {
    nameDropdown.style.display = 'none';
  }

  if (!tasksContainer.contains(target) && target.id != 'tasks-dropdown') {
    tasksDropdown.style.display = 'none';
  }
}

export function renderLogout(e) {
  e.preventDefault();

  const logoutContainer = document.createElement('div');
  logoutContainer.id = 'logout-container';
  logoutContainer.innerHTML = `
    <div id="confirm-container">
      <p>Are you sure you want to log out?</p>
      <div style="display: flex; gap: 100px;">
        <button id="confirm">Yes, log out</button>
        <button id="deny">No, stay here</button>
      </div>
    </div>
  `;

  document.getElementById('app').appendChild(logoutContainer);
  
  document.getElementById('confirm').addEventListener('click', logout);
  document.getElementById('deny').addEventListener('click', deny)
}

function logout() {
  localStorage.removeItem('auth');
  document.getElementById('logout-container')?.remove();
  renderLoginPage();
}

function deny() {
  document.getElementById('logout-container')?.remove();
}