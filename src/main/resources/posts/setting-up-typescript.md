---
title: Creating and setting up a TypeScript project
date: 2020-08-21T09:15:00
description: TypeScript brings static type definitions to JavaScript. In this post I will show how to create and setup a project with TypeScript using node.js
---

TypeScript brings static type definitions to JavaScript. In this post I will show how to create and setup a project with TypeScript using node.js.

First you will need to install node.js in case you don't already have node.js installed. For editing TypeScript files I recommend the VS Code editor, it already has support for it without installing extra extensions.

Then to install the TypeScript compiler globally you execute the following command:

```
npm install -g typescript ts-node
```

After that you create a directory for your project, for example, my-project.

Inside this directory, to init a project run the following commands:

```
npm --init -y

tsc --init
```

Running these commands will create a `package.json` that you can customize and also a `tsconfig.json`.

Inside your project directory, create two more directories: `src` and `build`. The `src` directory will contain your TypeScript source files and the `build` directory will have the compiled JavaScript files.

Open the `tsconfig.json` in the editor, discomment and modify the following lines to this:

```
outDir: "./build"
rootDir: "./src"
```

Now, to automatically compile and run your project, you will need to install two more libs:

```
npm install nodemon --save-dev
npm install concurrently --save-dev
```

`Nodemon` watches for modified .js files and execute them automatically. Concurrently will be used to run the `tsc` compiler and `nodemon` together.

Edit your `package.json` adding the following to the `scripts` section:

```
"start:build": "tsc -w",
"start:run": "nodemon build/index.js",
"start": "concurrently npm:start:*"
```

To execute the project run `npm start`. In this case the entry point of your application is in `src/index.ts` and it will be automatically compiled to `build/index.js`.

And that's it. Now you can work on your TypeScript projects.
