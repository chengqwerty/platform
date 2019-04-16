import {Observable, SubscriptionLike} from 'rxjs';

export interface SubscriptionStrategy {
    createSubscription(async: Observable<any>|Promise<any>, updateLatestValue: any): SubscriptionLike
        |Promise<any>;
    dispose(subscription: SubscriptionLike|Promise<any>): void;
    onDestroy(subscription: SubscriptionLike|Promise<any>): void;
}

export class ObservableStrategy implements SubscriptionStrategy {
    createSubscription(async: Observable<any>, updateLatestValue: any): SubscriptionLike {
        return async.subscribe({next: updateLatestValue, error: (e: any) => { throw e; }});
    }

    dispose(subscription: SubscriptionLike): void { subscription.unsubscribe(); }

    onDestroy(subscription: SubscriptionLike): void { subscription.unsubscribe(); }
}

export class PromiseStrategy implements SubscriptionStrategy {
    createSubscription(async: Promise<any>, updateLatestValue: (v: any) => any): Promise<any> {
        return async.then(updateLatestValue, e => { throw e; });
    }

    dispose(subscription: Promise<any>): void {}

    onDestroy(subscription: Promise<any>): void {}
}
