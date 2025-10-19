export function renderOwnProfile(userReadDto) {
  
  document.getElementById('app').innerHTML = 
  `
    ID: ${userReadDto.id} <br>
    Username: ${userReadDto.username} <br>
    Firstname: ${userReadDto.firstname} <br>
    Lastname: ${userReadDto.lastname} <br>
    Role: ${userReadDto.role}
  `;
}

export function renderEditOwnProfile() {
  document.getElementById('app').innerHTML = 
  `
    <input type="text" id="firstname" placeholder="Firstname">
    <input type="text" id="lastname" placeholder="Lastname">
    <button id="save">save</button>
  `
}