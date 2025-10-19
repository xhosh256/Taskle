import { nav } from "./properties.js";

export function showNav() {
  if (!document.body.contains(nav)) {
    const auth = JSON.parse(localStorage.getItem('auth'));

    nav.querySelector('#nav-name').innerHTML =
      `${auth.firstname} ${auth.lastname}`;
    document.body.prepend(nav);
  }
}

export function hideNav() {
  if (document.body.contains(nav)) {
    nav.remove();
  }
}