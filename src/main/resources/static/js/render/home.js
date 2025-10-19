import { addGroupsToNav, nav } from "../config/properties.js";
import { showNav } from "../config/utils.js";

export async function renderHomePage(page) {

  document.getElementById('app').innerHTML =
    `
    <div id='tasks-page'>
      <div id='tasks-page-title'>Tasks</div>

      <div id="tasks-filter">
        <button id='toggle-dropdown'>Filter</button>
        <a id="create-task">Create new task</a>
        <a id="create-group">Create new group</a>
        <div id='filter-dropdown'>
          <input type="text" id="task-name" placeholder="task name">
          <details>
            <summary style='cursor: pointer'>Tags</summary>
            <div class="tags-filter">
              <label style='cursor: pointer;'><input type="checkbox" value="WORK">Work</label>
              <label style='cursor: pointer;'><input type="checkbox" value="HOME">Home</label>
              <label style='cursor: pointer;'><input type="checkbox" value="STUDY">Study</label>
              <label style='cursor: pointer;'><input type="checkbox" value="PROJECT">Project</label>
              <label style='cursor: pointer;'><input type="checkbox" value="FINANCE">Finance</label>
              <label style='cursor: pointer;'><input type="checkbox" value="HEALTH">Health</label>
              <label style='cursor: pointer;'><input type="checkbox" value="FAMILY">Family</label>
              <label style='cursor: pointer;'><input type="checkbox" value="URGENT">Urgent</label>
              <label style='cursor: pointer;'><input type="checkbox" value="SOCIAL">Social</label>
              <label style='cursor: pointer;'><input type="checkbox" value="TRAVEL">Travel</label>
            </div>
          </details>

          <label> Page:
            <input type="number" id="page" placeholder="page" min=1 max=${page.totalPages} value=${page.pageable.pageNumber + 1}>
          </label>
          <button id="filter">Find</button>
        </div>
        
      </div>
    </div>
    
  `
  const div = document.createElement("div");
  div.id = 'my-tasks';

  for (const task of page.content) {
    const taskDiv = document.createElement('div');
    taskDiv.classList.add('task');
    taskDiv.innerHTML = `
      <p id='title'>${task.title}</p> 
      <p id='description'>${task.description}</p>
      <p id='tags'>Tags: ${task.tags}</p>

      <div class='btn-div'>
        <button class='edit-task-btn' data-id="${task.id}">Edit</button>
        <button class='delete-task-btn' data-id="${task.id}">Delete</button>
      </div>
      
    `
    div.appendChild(taskDiv);
  }

  showNav();
  document.getElementById('tasks-page').appendChild(div);
  document.getElementById('tasks-page').appendChild(nav);
  const links = document.querySelector('#nav-block .links');
  links.innerHTML = '';
  await addGroupsToNav();
}

export function renderCreateTaskPage() {
  document.getElementById('app').innerHTML =
    `
    <div id='create-task-page'>
      <div class='create-task-block'>
        <p style='font-size: 64px'>Create your task now!</p>

        <input type="text" id='task-name' placeholder='Title'>
        <textarea style='resize: none; height:200px' id='tesk-description' placeholder='Description'></textarea>

        <details>
          <summary style='font-family: Arial; font-size: 32px; cursor: pointer;'>Tags</summary>
          <div class="options">
            <label style='cursor: pointer;'><input type="checkbox" value="WORK">Work</label>
            <label style='cursor: pointer;'><input type="checkbox" value="HOME">Home</label>
            <label style='cursor: pointer;'><input type="checkbox" value="STUDY">Study</label>
            <label style='cursor: pointer;'><input type="checkbox" value="PROJECT">Project</label>
            <label style='cursor: pointer;'><input type="checkbox" value="FINANCE">Finance</label>
            <label style='cursor: pointer;'><input type="checkbox" value="HEALTH">Health</label>
            <label style='cursor: pointer;'><input type="checkbox" value="FAMILY">Family</label>
            <label style='cursor: pointer;'><input type="checkbox" value="URGENT">Urgent</label>
            <label style='cursor: pointer;'><input type="checkbox" value="SOCIAL">Social</label>
            <label style='cursor: pointer;'><input type="checkbox" value="TRAVEL">Travel</label>
          </div>
        </details>

        <button id='create-task-btn'>Create</button>
      </div>
    </div>
  `;

  showNav();
}

export function noSuchTasks() {
  document.getElementById('my-tasks').innerHTML = '';
}

export function renderTaskEditPage(task) {
  document.getElementById('app').innerHTML =
    `
    <div class='task-edit-page'>
      <form id = 'edit-form'>
        <input id='task-title' placeholder='Title'> <br>
        <textarea id='task-desc' placeholder='Description'></textarea> <br>
        <button class='confirm-edit' data-id="${task.id}">Confirm</button> <br>
      </form>
    </div>
  `;

  document.getElementById('task-title').value = task.title;
  document.getElementById('task-desc').value = task.description;

  showNav();
}