import {isAuthenticated, renderLoginPage} from './auth.js';
import { renderHomePage } from './home.js';

async function startApp() {
  const auth = await isAuthenticated();
  if(auth) {
    renderHomePage();
  } else {
    renderLoginPage();
  }
}

startApp();