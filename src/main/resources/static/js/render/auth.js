import { hideNav } from "../config/utils.js";

export function renderLoginPage() {
  hideNav();
  document.getElementById('app').innerHTML =
    `
    <div id="login-page">
      <div class='login-block'>
        <p style="font-size: 64px; text-align: center;">Log in now!</p>

        <form class="login-form">
          <input id="login" type="text" placeholder="Login" required>
          <input id="password" type="password" placeholder="Password" required> 
          <button type="submit" id="submit-login">Log in</button> 
        </form>

        <a id='register-link'>Or create a new account now!</a>
      </div>
    </div>

  `;
}

export function renderRegisterPage() {
  hideNav();
  document.getElementById('app').innerHTML =
    `
    <div id='register-page'>
      <div class='register-block'>
        <p style="font-size: 64px; text-align: center;">Create a new account!</p>

        <form class='register-form'>
          <input type='text' id='username' placeholder='Username' required maxlength="128">
          <input type='password' id='password' placeholder='Password' required minlength="5" maxlength="128">

          <div class="name-block">
            <input type='text' id='firstname' placeholder='Firstname' required maxlength="128">
            <input type='text' id='lastname' placeholder='Lastname' required maxlength="128">
          </div>

          <button type="submit" id='register-btn'>Register</button>
        </form>

        <a id='login-link'>Log in your account!</a>
      </div>
    </div>
  `
}

export function logout() {
  const logout = document.createElement('div');
  logout.id = 'logout-container';
  logout.innerHTML = 
  `
    <div id="confirm-container">
      <p>Are you sure you want to log out?</p>

      <div style="display: flex; gap: 100px;">
        <button id="confirm">Yes, log out</button>
        <button id="deny">No, stay here</button>
      </div>
    </div>
  `;

  document.getElementById('app').appendChild(logout);
}