import Router from "./Router.js";
import {home} from "./script.js";

const router = new Router({
    mode: 'hash',
    root: '/'
});

router
    .add(/create/, () => {
        alert('CREATE');
    })
    .add(/edit\/(.*)/, (id) => {
        alert(`edit id: ${id}`);
    })
    .add('', () => {
        home()
    });