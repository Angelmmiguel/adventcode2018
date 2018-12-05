use std::fs::File;
use std::io::BufReader;
use std::io::BufRead;

extern crate chrono;
use chrono::prelude::*;
use chrono::DateTime;

enum Action {
  Shift,
  Asleep,
  Awake
}

struct Line {
  id: String,
  date: DateTime<Utc>,
  action: Action
}

impl Line {
  fn new(line: Vec<&str>) -> Line {
    let date_string = &line[0..2].join(" ");
    let date: DateTime<Utc> = Utc.datetime_from_str(date_string, "[%Y-%m-%d %H:%M]").unwrap();
    let mut id: String = "-1".to_string();
    let mut action: Action = Action::Shift;

    if line[2] == "Guard" {
      id = line[3].to_string();
    } else if line[2] == "falls" {
      action = Action::Asleep;
    } else {
      action = Action::Awake;
    }

    Line {
      id: id,
      date: date,
      action: action
    }
  }
}

struct SleepTime {
  start: DateTime<Utc>,
  end: DateTime<Utc>,
  total: u32
}

struct Guard {
  id: u32,
  sleeps: Vec<SleepTime>
}

fn main() {
  let file = File::open("input.txt").unwrap();
  // Load the file content
  let content = BufReader::new(&file);

  // Read all lines
  let mut lines: Vec<String> = content.lines().map(|line| { line.unwrap() }).collect();
  lines.sort();

  let mut items: Vec<Line> = vec![];

  for l in lines.iter() {
    let mut data: Vec<&str> = l.split(" ").collect();
    let item: Line = Line::new(data);

    println!("{:?}", item.date);

    items.push(item)
  }
}
