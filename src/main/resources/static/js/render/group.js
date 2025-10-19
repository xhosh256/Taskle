import { fetchGroup } from "../api/group.js";
import { fetchAllTasks } from "../api/task.js";
import { showNav } from "../config/utils.js"

export function renderCreateGroupPage() {
  document.getElementById('app').innerHTML = 
  `
    <div class='create-group-page'>
      <form id='create-group-form'>
        <input id='group-name' placeholder='Name'>
        <button type='submit'>Create</button>
      </form>
    </div>
    
  `

  showNav()
}

export async function renderGroup(auth, id) {
  const group = await fetchGroup(auth, id);

  const div = document.createElement("div");
  div.id = 'group-tasks';

  for (const task of group.tasks) {
    const taskDiv = document.createElement('div');
    taskDiv.classList.add('task');
    taskDiv.innerHTML = `
      <p id='title'>${task.title}</p> 
      <p id='description'>${task.description}</p>
      <p id='tags'>Tags: ${task.tags}</p>

      <div class='btn-div'>
        <button class='edit-task-btn' data-id="${task.id}">Edit</button>
        <button class='delete-task-from-group-btn' data-id="${task.id}">Remove</button>
        <button class='delete-task-btn' data-id="${task.id}">Delete</button>
      </div>
      
    `
    div.appendChild(taskDiv);
  }

  document.getElementById('app').innerHTML = 
  `
    <div id='group-page'>
      <div id='name'>${group.name}</div>
      <div id='buttons'>
        <button class='add-task-to-group-btn' data-id="${group.id}">Add Task</button>
        <button class='delete-group-btn' data-id="${group.id}">Delete Group</button>
        
      </div>
    </div>
  `;
  document.getElementById('group-page').appendChild(div);
  showNav();
}

export async function renderAddTaskToGroup(id) {
  const auth = JSON.parse(localStorage.getItem('auth'));
  
  const form = document.createElement('form');
  form.id = 'add-tasks-form';

  const tasks = await fetchAllTasks(auth);

  for(const task of tasks) {
    const div = document.createElement('div');
    div.classList.add('task-to-add');
    div.innerHTML = 
    `
      <div class='task-to-add'>
        <label><p class='title'>${task.title}</p>
          <input type="checkbox" data-id="${task.id}">
        </label>
      </div>     
    `;

    form.appendChild(div);
  }
  const idBlock = document.createElement('input');
  idBlock.id = 'group-id';
  idBlock.type = 'hidden';
  idBlock.value = id;

  const submit = document.createElement('button');
  submit.id = 'add-tasks-btn';
  submit.type = 'button';
  submit.innerHTML = 'Add slected tasks';
  form.appendChild(submit);

  document.getElementById('app').innerHTML = '';
  document.getElementById('app').appendChild(form);
  document.getElementById('app').appendChild(idBlock);
}