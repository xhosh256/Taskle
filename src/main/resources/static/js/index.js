import { checkAuth } from "./api/auth.js";
import { handleLogin, handleRegister } from "./events/auth.js";
import { logout, renderLoginPage, renderRegisterPage } from "./render/auth.js";
import { renderCreateTaskPage, renderTaskEditPage } from "./render/home.js";
import { createTask, handleEdit, tasksPaginationAndFilter } from "./events/home.js";
import { deleteTask, fetchTaskById } from "./api/task.js";
import { renderAddTaskToGroup, renderCreateGroupPage, renderGroup } from "./render/group.js";
import { createGroup, deleteGroup, getAllChecked, removeTaskFromGroup } from "./events/group.js";

async function startApp() {
  const authenticated = await checkAuth();

  if (authenticated) {
    tasksPaginationAndFilter(0, { "title": '', 'tags': [] });
  } else {
    renderLoginPage();
  }
}

document.addEventListener('submit', async (e) => {
  if (e.target.matches('.login-form')) {
    e.preventDefault();
    const loginPage = document.querySelector('#login-page');

    const login = loginPage.querySelector('#login').value;
    const password = loginPage.querySelector('#password').value;

    await handleLogin(login, password);
  }

  else if (e.target.matches('.register-form')) {
    e.preventDefault();

    const registerPage = document.querySelector('#register-page');

    const username = registerPage.querySelector('#username').value;
    const password = registerPage.querySelector('#password').value;
    const firstname = registerPage.querySelector('#firstname').value;
    const lastname = registerPage.querySelector('#lastname').value;

    handleRegister({ username, password, firstname, lastname });
  }

  else if (e.target.id === 'edit-form') {
    e.preventDefault();
    const editPage = document.querySelector('.task-edit-page');

    const title = editPage.querySelector('#task-title').value;
    const description = editPage.querySelector('#task-desc').value;
    const id = editPage.querySelector('.confirm-edit').dataset.id;

    await handleEdit(id, {title, description});    
  }

  else if (e.target.id === 'create-group-form') {
    e.preventDefault();
    const createPage = document.querySelector('.create-group-page');

    const name = createPage.querySelector('#group-name').value;

    await createGroup({name});
  }
})

document.addEventListener('click', async (e) => {
  const filterDropdown = document.getElementById('filter-dropdown');
  const profileDropdown = document.querySelector('.profile-dropdown');

  if (e.target.id === 'filter') {
    e.preventDefault();
    const filter = document.querySelector('#tasks-filter');

    const title = filter.querySelector('#task-name').value;
    const page = filter.querySelector('#page').value - 1;
    const tags = [];

    const checkboxes = filter.querySelector('.tags-filter').querySelectorAll('input[type=checkbox]');

    Array.from(checkboxes)
      .filter(cb => cb.checked)
      .forEach(element => tags.push(element.value));


    await tasksPaginationAndFilter(page, { title, tags });
  }

  else if (e.target.id === 'create-task') {
    e.preventDefault();
    renderCreateTaskPage();
  }

  else if (e.target.id === 'create-task-btn') {
    e.preventDefault();

    const createTaskPage = document.querySelector('#create-task-page');

    const taskTitle = createTaskPage.querySelector('#task-name').value;
    const taskDescription = createTaskPage.querySelector('#tesk-description').value;
    const tags = [];

    const checkboxes = createTaskPage.querySelector('.options').querySelectorAll('input[type=checkbox]');

    Array.from(checkboxes)
      .filter(cb => cb.checked)
      .forEach(element => tags.push(element.value));

    createTask({
      title: taskTitle,
      description: taskDescription,
      tags
    });
  }

  else if (e.target.id === 'register-link') {
    e.preventDefault();
    renderRegisterPage();
  }

  else if (e.target.id === 'login-link') {
    e.preventDefault();
    renderLoginPage();
  }

  else if (e.target.id === 'toggle-dropdown') {
    filterDropdown.classList.toggle('show');
  }

  else if (e.target.id === 'nav-name') {
    profileDropdown.classList.toggle('show');
  }

  else if (e.target.id === 'logout-btn') {
    e.preventDefault();
    logout();
  }

  else if (e.target.id === 'confirm') {
    e.preventDefault();
    localStorage.removeItem('auth');
    renderLoginPage();
  }

  else if (e.target.id === 'deny') {
    e.preventDefault();
    document.getElementById('logout-container').remove();
  }

  else if (filterDropdown && !e.target.closest('#filter-dropdown') && filterDropdown.classList.contains('show')) {
    filterDropdown.classList.remove('show');
  }

  else if (profileDropdown && !e.target.closest('.profile-dropdown') && profileDropdown.classList.contains('show')) {
    profileDropdown.classList.remove('show');
  }

  else if (e.target.id === 'tasks-link') {
    e.preventDefault();
    await tasksPaginationAndFilter(0, { "title": '', 'tags': [] });
  }

  else if (e.target.classList.contains('edit-task-btn')) {
    e.preventDefault();
    const id = e.target.dataset.id;
    const taskReadDto = await fetchTaskById(id);

    renderTaskEditPage(taskReadDto);
  }

  else if (e.target.classList.contains('delete-task-btn')) {
    e.preventDefault();
    const id = e.target.dataset.id;
    
    const res = await deleteTask(id);

    console.log(res);
    await tasksPaginationAndFilter(0, { "title": '', 'tags': [] });
  }

  else if (e.target.id === 'create-group') {
    e.preventDefault();
    renderCreateGroupPage();
  }

  else if (e.target.classList.contains('group-link')) {
    const id = e.target.dataset.id;
    const auth = JSON.parse(localStorage.getItem('auth'));

    await renderGroup(auth, id);    
  }

  else if (e.target.classList.contains('delete-group-btn')) {
    const id = e.target.dataset.id;

    await deleteGroup(id);
  }

  else if(e.target.classList.contains('add-task-to-group-btn')) {
    e.preventDefault();

    const id = document.querySelector(".add-task-to-group-btn").dataset.id;
    await renderAddTaskToGroup(id);
  }

  else if (e.target.id === 'add-tasks-btn') {
    e.preventDefault();

    const id = document.getElementById('group-id').value;
    await getAllChecked(id);
  }

  else if (e.target.classList.contains('delete-task-from-group-btn')) {
    e.preventDefault();

    const taskId = e.target.dataset.id;
    const groupId = document.querySelector('.add-task-to-group-btn').dataset.id;

    const auth = JSON.parse(localStorage.getItem('auth'));
    await removeTaskFromGroup(auth, taskId, groupId);
  }

});

startApp();
