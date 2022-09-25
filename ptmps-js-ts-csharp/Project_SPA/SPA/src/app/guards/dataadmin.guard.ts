import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthenticationService } from '../authentication.service';


@Injectable()
export class DataAdminGuard implements CanActivate {
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService
    ) { }


    canActivate() {
        if (this.authenticationService.userInfo)
            if (this.authenticationService.userInfo.dataAdmin)
                return true;
        this.router.navigate(['/login', { u: 'no' }]);
        return false;
    }
}