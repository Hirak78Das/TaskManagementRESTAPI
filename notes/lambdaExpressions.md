# Java Lambda Expression

--> Lambda Expression are methods without any name, used inside a method which takes parameter and return it

## using enhance for loop vs lambda Expression with forEach()

Approach 1: using enhance for loop :

`public Task getTaskByID(int id) {
    for (Task task : tasks) {
      System.out.println(task);
 }
}
`
Approach 2: Using a Lambda Expression with forEach()
`public Task getTaskByID(int id) {
    return tasks.forEach(task -> System.out.println(task));
}
`

## use _stream()_ with lambda only when you need to filter out any object from the list, or map etc.

## use _forEach()_ directly with lambda without using steam() if you want to just print the list objects.

## Time complexity of both Approach in worst case O(n),

# Summary

_Both Approaches have same TC, hence the choice between them would depend on readability and personal preference_
