export default class Router {
    routes = [];

    constructor() {
        window.addEventListener('load', this.work);
    }

    add = (path, callback) => {
        this.routes.push({path, callback});
        return this;
    };

    work = () => this.routes.find(route => route.path===window.location.pathname)
        .callback();
}