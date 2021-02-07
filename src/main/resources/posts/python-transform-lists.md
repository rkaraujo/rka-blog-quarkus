---
title: How to map and transform lists in Python
date: 2021-02-04T07:30:00
description: Examples of how to map and transform lists in Python
---

Some examples of how to map and transform lists in Python.

## Example 1:

Get all names from results into a new list:

```
names = [result.name for result in results]
```

## Example 2:

Get companies from results into a new set:

```
companies = {result.company for result in results}
```

## Example 3:

Create a dict using id as key and name as value:

```
name_by_id = {result.id: result.name for result in results}
```
