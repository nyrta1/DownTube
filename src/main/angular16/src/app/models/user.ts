export class User {
    readonly username: string;
    readonly password: string;
    readonly roles: string[];

    constructor(username: string, password: string, roles: string[]) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
