# RESFTful API

## Assignment A

### Object Model

**Customer**
- id 
- type
- name
- email
- createdAt

**Product**
- id
- name
- description
- createdAt

**Plan**
- id
- product
- name
- description
- price
- createdAd

**Subscription**
- id
- customer
- plan
- status
- startDate
- endDate
- createdAt

### Functional Extensions

1. Trial subscriptions

Give customers an option to try out a subscription before committing to it.
The simplest way we can achieve that is by extending table `Subscription` with fields `isTrial` and `trialEndDate`.

2. Putting subscriptions on hold

Give customers an option to pause subscription for shorter periods of time instead of cancelling it.
The simplest way we can achieve that is by extending table `Subscription` with fields `startDate` and `endDate`. 

3. Product bundles

Give customers an option to purchase a bundle of multiple products by incentivizing them with lower total price.
The simplest way we can achieve that is by introducing new table `PlanProduct` that updates relation of tables `Plan` and `Product` into many-to-many relation. 

4. Promotions

Give customers and option to use promotion code for reduced subscription price.
The simplest way we can achieve that is by introducing new table `Discount` with fields `id`, `code`, `type`, `value`, `startDate`, and `endDate` and by extending table `Subscription` with a FK for `Discount.id`.


