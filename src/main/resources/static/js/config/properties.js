import { fetchGroups } from "../api/group.js";

export const API = "http://localhost:8080/api/v1";

export const nav = document.createElement('nav');
nav.id = 'nav-block'
nav.innerHTML = 
`
  
  <a id='nav-name'>Firstname Lastname</a>
  <div class='profile-dropdown'>
    <button id='logout-btn'>Log out</button>
  </div>
    <a class='link' id='tasks-link'>Tasks</a>
  <div class='links'>
    
  </div>
  
`;

addGroupsToNav();
export async function addGroupsToNav() {
  const links = nav.querySelector('.links');
  const auth = JSON.parse(localStorage.getItem('auth'));

  const groups = await fetchGroups(auth);

  for(const group of groups) {
    const link = document.createElement('a');
    link.classList.add('link');
    link.classList.add('group-link');
    link.dataset.id = group.id;
    link.innerHTML = group.name;
    links.appendChild(link);
  }
}