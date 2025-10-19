import { executeLogin, executeRegister } from "./auth-utils.js";

const API = 'http://localhost:8080/api/v1';
const app = document.getElementById('app');

export async function isAuthenticated() {
  const auth = JSON.parse(localStorage.getItem('auth'));

  if (auth == null) {
    return false;
  }

  const res = await fetch(`${API}/users/test`, {
    method: 'GET',
    headers: { 'Authorization': `Bearer ${auth.token}` }
  });

  if (!res.ok) {
    return false;
  }

  return true;
}

export function renderLoginPage() {
  document.title = 'Log in';
  app.innerHTML =
    `
    <div class="login-body">
      <div class="login-block">
        <p style="font-size: 64px; text-align: center;">Log in now!</p>

        <form id="login-form" method="POST">
          <input type="text" id="username" placeholder="Username" required>
          <input type="password" id="password" placeholder="Password" required>
          <button type="submit" id="login-btn">Log in</button>
        </form>

        <div class="create-new-acc-div">
          <button id="register-link">Or create a new account now!</button>
        </div>

      </div>
    </div>
  `;

  document.getElementById('login-form').addEventListener('submit', executeLogin);
  document.getElementById('register-link').addEventListener('click', (e) => {
    e.preventDefault();

    renderRegisterPage();
  })
}

export function renderRegisterPage() {
  document.title = 'Register';
  app.innerHTML =
    `
    <div class="register-body">
    <div class="register-block">
      <p style="font-size: 64px; text-align: center;">Create a new account!</p>

      <form id="register-form" method="POST">
        <input type="text" name="username" id="username" placeholder="Username" required>
        
        <input type="password" name="password" id="password" placeholder="Password" required>

        <div class="name-block">
          <input type="text" name="firstname" id="firstname" placeholder="Firstname" required>

          <input type="text" name="lastname" id="lastname" placeholder="Lastname" required>
        </div>
        

        <button type="submit" id="register-btn">Register</button>
      </form>

      <div class="create-new-acc-div">
        <button id="login-link">Log in your account!</button>
      </div>

    </div>
  </div>
  `;

  document.getElementById('register-form').addEventListener('submit', (e) => {
    try {
      executeRegister(e);
      renderLoginPage();
    } catch (err) {
      console.log(err);
    }
  });
  document.getElementById('login-link').addEventListener('click', (e) => {
    e.preventDefault();

    renderLoginPage();
  })
}