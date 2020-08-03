import Router from "./Router.js";
import {navigateMain} from "./script.js";
// import {navigateCreateContact} from "./script.js";
// import {navigateEditContact} from "./script.js";

const router = new Router();

router.add('/', () => {navigateMain()})
// .add('/create', () => {navigateCreateContact()})
//     .add('/edit', () => {navigateEditContact()});