#include <iostream>
#include <fstream>
#include <regex>
#include <algorithm>
#include <map>

typedef struct {
  int month;
  int day;
  int hour;
  int min;
  std::string action;
  std::string guard_id;
} entry;

bool sortOrder(entry a, entry b) {
  if (a.month == b.month) {
    if (a.day == b.day) {
      return a.hour * 60 + a.min < b.hour * 60 + b.min;
    } else {
      return a.day < b.day;
    }
  } else {
    return a.month < b.month;
  }
}

entry parseEntry(std::string p) {
  std::smatch match;
  std::regex rgx("^\\[\\d{4}-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})\\] (\\w+) ([#\\w ]+)");

  std::regex_search(p, match, rgx);

  entry parsedEntry;
  parsedEntry.month = std::stoi(match[1]);
  parsedEntry.day = std::stoi(match[2]);
  parsedEntry.hour = std::stoi(match[3]);
  parsedEntry.min = std::stoi(match[4]);
  parsedEntry.action = match[5];
  parsedEntry.guard_id = match[6];

  return parsedEntry;
}

int main(int argc, char* argv[]) {
  std::ifstream fs(argv[1], std::ifstream::in);
  std::vector<entry> entries;
  std::map<int, int*> guardToMinutes;
  std::map<int, int> guardToMinutesTotal;

  std::string line;
  while (std::getline(fs, line)) {
    auto p = parseEntry(line);
    entries.push_back(p);
  }

  fs.close();

  std::sort(entries.begin(), entries.end(), sortOrder);

  int currentGuard = 0;
  int minuteSleeping = -1;

  for (auto e : entries) {
    if (e.action == "falls") {
      minuteSleeping = e.min;
    } else if (e.action == "wakes") {
      if (guardToMinutesTotal.count(currentGuard) == 0) guardToMinutesTotal[currentGuard] = 0;

      if (guardToMinutes.count(currentGuard) == 0) {
        guardToMinutes[currentGuard] = new int[60];
        for (int i = 0; i < 60; i++) guardToMinutes[currentGuard][i] = 0;
      }

      for (int i = minuteSleeping; i < e.min; i++) {
        guardToMinutes[currentGuard][i]++;
        guardToMinutesTotal[currentGuard]++;
      }
    } else {
      std::smatch match;
      std::regex rgx("#(\\d+) begins shift");
      std::regex_search(e.guard_id, match, rgx);
      currentGuard = std::stoi(match[1]);
    }
  }

  int guardSleepingTheMost = -1;
  int guardSleepingTheMostTotalMinutes = -1;
  for (auto g : guardToMinutesTotal) {
    if (g.second > guardSleepingTheMostTotalMinutes) {
      guardSleepingTheMostTotalMinutes = g.second;
      guardSleepingTheMost = g.first;
    }
  }


  int minuteWhereMostSlept = -1;
  int minuteSleptMost = -1;
  for (int i = 0; i < 60; i++) {
    auto g = guardToMinutes[guardSleepingTheMost][i];
    if (g > minuteSleptMost) {
      minuteSleptMost = g;
      minuteWhereMostSlept = i;
      i++;
    }
  }

  std::cout << "Result " << guardSleepingTheMost * minuteWhereMostSlept << std::endl;

  for (auto g : guardToMinutes) {
    delete g.second;
  }

  return EXIT_SUCCESS;
}
