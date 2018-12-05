#!/usr/bin/env ruby

GUARD_REGEX = /Guard #(?<guard_id>\d+)/
MINUTE_REGEX = /\[\w+-\w+-\w+\s\w+:(?<minute>\w+)/

class Log
  attr_reader :year, :month, :day, :hour, :minute, :message, :guard_id

  def initialize(year, month, day, hour, minute, message)
    @year = year.to_i
    @month = month.to_i
    @day = day.to_i
    @hour = hour.to_i
    @minute = minute.to_i
    @guard_id = GUARD_REGEX.match(message) && GUARD_REGEX.match(message)[:guard_id]
    @message = message
  end
end

schedule = []

lines = File.readlines('input.txt')

lines.sort.each do |line|
  match = /\[(?<year>\d+)-(?<month>\d+)-(?<day>\d+)\s(?<hour>\d+):(?<minute>\d+)\](?<message>.+)/.match(line)
  schedule << Log.new(
    match[:year],
    match[:month],
    match[:day],
    match[:hour],
    match[:minute],
    match[:message]
  )
end

@asleep_records = {}
@current_guard = nil
@asleep_min = nil

schedule.each do |log|
  case log.message
  when /Guard/
    @current_guard = log.guard_id
  when /falls/
    @asleep_min = log.minute
  when /wakes/
    @asleep_records[@current_guard] ||= Array.new(60, 0)
    wakeup_min = log.minute
    (@asleep_min...wakeup_min).each do |minute|
      @asleep_records[@current_guard][minute] += 1
    end
  end
end

sleepyhead_guard = nil
guard_asleep_minutes = 0
guard_selected_minute = 0

puts 'PART 1'

@asleep_records.each do |guard, minutes|
  asleep_minutes = minutes.sum

  next unless asleep_minutes > guard_asleep_minutes

  guard_asleep_minutes = asleep_minutes
  guard_selected_minute = minutes.index(minutes.max)
  sleepyhead_guard = guard
end

puts "Guard: #{sleepyhead_guard}, minute: #{guard_selected_minute}, total minutes asleep: #{guard_asleep_minutes}"

puts "Solution: #{sleepyhead_guard.to_i * guard_selected_minute}"

puts 'PART 2'

sleepyhead_guard = nil
most_frequently_minute = nil
max_asleep_minutes = 0

@asleep_records.each do |guard, minutes|
  max_minutes = minutes.max

  next unless max_minutes > max_asleep_minutes

  max_asleep_minutes = max_minutes
  most_frequently_minute = minutes.index(max_asleep_minutes)
  sleepyhead_guard = guard
end

puts "Guard: #{sleepyhead_guard}, minute: #{most_frequently_minute}, minutes asleep: #{max_asleep_minutes}"

puts "Solution: #{sleepyhead_guard.to_i * most_frequently_minute}"
